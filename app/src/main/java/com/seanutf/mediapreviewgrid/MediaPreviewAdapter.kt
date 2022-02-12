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

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mediaList: List<Media>?) {
        this.mediaList = mediaList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaPreviewItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_media_preview_default, parent, false)
        return MediaPreviewItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MediaPreviewItemViewHolder, position: Int) {
        holder.setData(mediaList?.get(position))
    }

    override fun getItemCount(): Int {
        return mediaList?.size ?: 0
    }
}