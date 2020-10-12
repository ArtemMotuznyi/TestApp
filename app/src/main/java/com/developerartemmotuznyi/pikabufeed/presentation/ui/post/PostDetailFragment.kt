package com.developerartemmotuznyi.pikabufeed.presentation.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.developerartemmotuznyi.pikabufeed.databinding.FragmentPostDetailBinding
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.presentation.base.BaseViewModelFragment
import com.developerartemmotuznyi.pikabufeed.presentation.ui.feed.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailFragment : BaseViewModelFragment<FragmentPostDetailBinding, PostDetailViewModel>() {

    override val viewModel: PostDetailViewModel by viewModels()

    private val args by navArgs<PostDetailFragmentArgs>()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPostDetailBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPost(args.postId)
    }

    override fun initSubscription() {
        viewModel.post.observe(viewLifecycleOwner) {
            it?.let(::initPost)
        }
    }

    private fun initPost(post: Post) {
        binding.favorite.setOnClickListener { viewModel.updateStatePost(post) }

        binding.title.text = post.title

        binding.body.isVisible = !post.body.isBlank()
        binding.body.text = post.body

        binding.favorite.isSelected = post.isSaved

        if (post.images.isNotEmpty()) {
            binding.images.isVisible = true
            binding.images.adapter = ImagesAdapter(post.images)
        } else {
            binding.images.isVisible = false
        }
    }
}