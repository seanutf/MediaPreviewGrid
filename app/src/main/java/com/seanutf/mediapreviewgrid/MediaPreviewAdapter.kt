package com.seanutf.mediapreviewgrid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seanutf.media.queryprovider.data.Media

/**
 * @Author seanutf
 * @Date 2022/2/4 11:40 上午
 * @Desc 媒体列表适配器
 */
class MediaPreviewAdapter : RecyclerView.Adapter<MediaPreviewItemViewHolder>() {

    private var mediaList: List<Media>? = null
    private var clickMediaListener: MediaItemClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mediaList: List<Media>?) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }

    fun setMediaListener(clickMediaListener: MediaItemClickListener?) {
        this.clickMediaListener = clickMediaListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaPreviewItemViewHolder {
        return MediaPreviewItemViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MediaPreviewItemViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.setData(mediaList?.get(position), object : MediaPreviewItemClickListener{
            override fun onClickItem() {
                clickMediaListener?.onClickMedia(mediaList?.get(position), position, mediaList)
            }
        })
    }

    override fun getItemCount(): Int {
        return mediaList?.size ?: 0
    }
}