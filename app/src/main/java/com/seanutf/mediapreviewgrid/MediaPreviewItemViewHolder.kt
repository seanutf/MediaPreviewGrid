package com.seanutf.mediapreviewgrid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.seanutf.media.queryprovider.data.Media
import com.seanutf.mediapreviewgrid.databinding.ItemMediaPreviewBinding

/**
 * @Author seanutf
 * @Date 2022/2/4 11:41 上午
 * @Desc 媒体预览列表的ViewHolder
 */
class MediaPreviewItemViewHolder(private val itemVb: ItemMediaPreviewBinding) : RecyclerView.ViewHolder(itemVb.root) {

    companion object {
        fun createViewHolder(parent: ViewGroup): MediaPreviewItemViewHolder {
            val vb = ItemMediaPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MediaPreviewItemViewHolder(vb)
        }
    }

    fun setData(media: Media?) {
        media?.let {
            if (media.isVideo) {
                Glide.with(itemView.context).load(media.mediaPath).transition(DrawableTransitionOptions.withCrossFade()).into(itemVb.ivPhoto)
                showVideoInfo(true, media.duration)
                showImgInfo(false)
            } else {
                Glide.with(itemView.context).load(media.mediaPath).into(itemVb.ivPhoto)
                showVideoInfo(false, 0)
                if (media.isGif){
                    showImgInfo(true)
                } else {
                    showImgInfo(false)
                }
            }
        }
    }

    private fun showImgInfo(show: Boolean) {
        itemVb.ivGifTag.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun showVideoInfo(show: Boolean, duration: Long) {
        if (show) {
            val durStr = getVideoDuration(duration / 1000)
            itemVb.tvVideoDuration.text = durStr
            itemVb.tvVideoDuration.visibility = View.VISIBLE
            itemVb.videoTag.visibility = View.VISIBLE
        } else {
            itemVb.tvVideoDuration.text = ""
            itemVb.tvVideoDuration.visibility = View.INVISIBLE
            itemVb.videoTag.visibility = View.INVISIBLE
        }
    }

    private fun getVideoDuration(duration: Long): String {
        var tempDuration = "00:00"
        if (duration > 0) {
            val hour = (duration / (60 * 60)).toInt()
            val minute = (duration % (60 * 60) / 60).toInt()
            val second = (duration % (60 * 60) % 60).toInt()
            tempDuration = if (hour > 0) {
                String.format("%02d:%02d:%02d", hour, minute, second)
            } else {
                String.format("%02d:%02d", minute, second)
            }
        }
        return tempDuration
    }
}