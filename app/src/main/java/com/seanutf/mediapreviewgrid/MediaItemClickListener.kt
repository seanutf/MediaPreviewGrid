package com.seanutf.mediapreviewgrid

import com.seanutf.media.queryprovider.data.Media

/**
 * @Author seanutf
 * @Date 2022/5/27 4:22 下午
 * @Desc 媒体item点击的回调监听
 */
interface MediaItemClickListener {

    /**
     * 点击媒体显示iteem时返回媒体数据
     * */
    fun onClickMedia(media: Media?, position: Int = 0, mediaList: List<Media>?)

}