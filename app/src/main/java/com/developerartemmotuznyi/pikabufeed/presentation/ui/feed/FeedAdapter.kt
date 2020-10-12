package com.developerartemmotuznyi.pikabufeed.presentation.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.developerartemmotuznyi.pikabufeed.databinding.ItemPostBinding
import com.developerartemmotuznyi.pikabufeed.domain.model.Post

interface FeedPostActionListener {

    fun onPostClick(post: Post)

    fun onLikeClick(post: Post)

}

class FeedAdapter(
    private val onFeedPostActionListener: FeedPostActionListener
) : ListAdapter<Post, PostHolder>(differ) {

    companion object {
        private val differ = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.isSaved == newItem.isSaved
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder =
        PostHolder(bindView(ItemPostBinding::inflate, parent), onFeedPostActionListener)

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    private fun <VB : ViewBinding> bindView(
        viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> VB,
        parent: ViewGroup
    ): VB = viewBindingFactory(LayoutInflater.from(parent.context), parent, false)
}

class PostHolder(
    private val binding: ItemPostBinding,
    private val onFeedPostActionListener: FeedPostActionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.root.setOnClickListener { onFeedPostActionListener.onPostClick(post) }
        binding.favorite.setOnClickListener { onFeedPostActionListener.onLikeClick(post) }

        binding.title.text = post.title

        binding.body.isVisible = !post.body.isBlank()
        binding.body.text = post.body

        binding.favorite.isSelected = post.isSaved

        val image = post.images.firstOrNull()
        if (!image.isNullOrBlank()) {
            binding.image.isVisible = true
            Glide.with(binding.image).load(image).into(binding.image)
        } else {
            binding.image.isVisible = false
        }

    }

}
