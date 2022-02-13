package com.seanutf.mediapreviewgrid

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seanutf.media.queryprovider.config.QueryConfig
import com.seanutf.media.queryprovider.core.MediaQueryProvider
import com.seanutf.media.queryprovider.data.Album
import com.seanutf.media.queryprovider.data.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author seanutf
 * @Date 2022/2/4 1:33 下午
 * @Desc 媒体列表也没的ViewModel
 */
class MediaPreviewViewModel : ViewModel() {
    var showLoading = MutableLiveData<Boolean>()
    var uiShowState = MutableLiveData<UiShowState>()
    var albumListData = MutableLiveData<List<Album>>()

    private var currentAlbum: Album? = null
    private var currentAlbumList: List<Album>? = null
    private val mediaProvider = MediaQueryProvider()
    private var needLoadAlbum = true

    fun setConfig(application: Application, queryConfig: QueryConfig) {
        mediaProvider.setConfig(application, queryConfig)
    }

    fun loadMediaData() {
        uiShowState.value = UiShowState.LOADING
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.Default) {
                loadMedias2()
            }
        }
    }

    fun getMediaStoreList(): List<Media>? {
        return currentAlbum?.mediaList
    }

    private fun loadMedias2() {
        var error = false
        var needLoadAlbum = false

        //确定当前所在文件夹
        if(currentAlbum != null){
            if(currentAlbum?.mediaList.isNullOrEmpty()){
                //根据当前所在文件夹获取文件夹下的当前类型所有媒体
                val mediaList = mediaProvider.loadAlbumMedias(currentAlbum!!.bucketId, needLoadAlbum)
                if(mediaList.isNullOrEmpty()){
                    error = true
                } else {
                    currentAlbum?.mediaList = mediaList
                }
            }
        } else {
            if(mediaProvider.getAlbumList().isNullOrEmpty()){
                needLoadAlbum = true
                val mediaList = mediaProvider.loadAlbumMedias(-1, needLoadAlbum)
                if(!mediaProvider.getAlbumList().isNullOrEmpty()){
                    currentAlbumList = mediaProvider.getAlbumList()
                    currentAlbum = mediaProvider.getAlbumList()!![0]
                    currentAlbum!!.mediaList = mediaList
                } else {
                    error = true
                }
            } else {
                currentAlbum = mediaProvider.getAlbumList()!![0]
                if(currentAlbum?.mediaList.isNullOrEmpty()){
                    //根据当前所在文件夹获取文件夹下的当前类型所有媒体
                    currentAlbum?.mediaList = mediaProvider.loadAlbumMedias(currentAlbum!!.bucketId, needLoadAlbum)
                }
            }
        }

        //以下逻辑均是load过程已经结束，不需要子线程，所以使用postValue进行切换线程通知
        if (needLoadAlbum && !currentAlbumList.isNullOrEmpty()) {
            needLoadAlbum = false
            albumListData.postValue(currentAlbumList!!)
        }

        if (error) {
            uiShowState.postValue(UiShowState.ERROR)
        } else {
            uiShowState.postValue(UiShowState.SUCCESS)
        }
    }
}