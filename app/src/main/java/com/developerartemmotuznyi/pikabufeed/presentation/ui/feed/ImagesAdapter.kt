package com.developerartemmotuznyi.pikabufeed.presentation.ui.feed

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImagesAdapter(images: List<String>) : ListAdapter<String, ImageViewHolder>(differ) {
    companion object {
        private val differ = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    init {
        submitList(images)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(AppCompatImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        })
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }
}

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(path: String) {
        if (itemView is AppCompatImageView) {
            Glide.with(itemView)
                .load(path)
                .centerCrop()
                .into(itemView)
        }
    }
}