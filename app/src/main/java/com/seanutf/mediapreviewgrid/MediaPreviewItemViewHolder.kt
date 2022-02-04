package com.seanutf.mediapreviewgrid

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.seanutf.mediapreviewprovider.data.Media

/**
 * @Author seanutf
 * @Date 2022/2/4 11:41 上午
 * @Desc 媒体预览列表的ViewHolder
 */
class MediaPreviewItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun setData(media: Media?) {
        media?.let {
            val photo = itemView.findViewById<ImageView>(R.id.ivPhoto)
            if (media.isVideo) {
                Glide.with(itemView.context).load(media.mediaPath).transition(DrawableTransitionOptions.withCrossFade()).into(photo)

//                val duration = getVideoDuration(mediaItem.duration / 1000)
//                itemVb.tvVideoDuration.text = duration
//                itemVb.tvVideoDuration.visibility = View.VISIBLE
//                itemVb.videoTag.visibility = View.VISIBLE
                //itemVb.llSelectNum.visibility = View.GONE
            } else {
                Glide.with(itemView.context).load(media.mediaPath).into(photo)
            }
        }
    }
}