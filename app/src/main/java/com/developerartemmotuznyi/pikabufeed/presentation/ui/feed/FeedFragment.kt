package com.developerartemmotuznyi.pikabufeed.presentation.ui.feed

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developerartemmotuznyi.pikabufeed.R
import com.developerartemmotuznyi.pikabufeed.databinding.FragmentFeedBinding
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.presentation.base.BaseViewModelFragment
import com.developerartemmotuznyi.pikabufeed.presentation.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize


sealed class FeedType : Parcelable {
    @Parcelize
    object Saved : FeedType()

    @Parcelize
    object Common : FeedType()
}

@AndroidEntryPoint
class FeedFragment : BaseViewModelFragment<FragmentFeedBinding, FeedViewModel>(),
    FeedPostActionListener {

    companion object {
        private const val FEED_TYPE = "feed_type"

        fun newInstance(type: FeedType) = FeedFragment().apply {
            arguments = bundleOf(FEED_TYPE to type)
        }
    }

    private val type: FeedType
        get() = requireArguments().getParcelable(FEED_TYPE)
            ?: throw IllegalArgumentException()

    override val viewModel: FeedViewModel by viewModels({ parentFragment ?: this })

    private val adapter = FeedAdapter(this)

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFeedBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    override fun initSubscription() {
        if (type is FeedType.Saved) {
            viewModel.savedPosts.observe(viewLifecycleOwner) {
                setSavedList(it.orEmpty())
            }
        } else {
            viewModel.allPosts.observe(viewLifecycleOwner) {
                setLoadedList(it.orEmpty())
            }
        }
    }

    private fun setLoadedList(list: List<Post>) {
        updateLoadedEmptyState(list.isEmpty())
        adapter.submitList(list)
    }

    private fun updateLoadedEmptyState(empty: Boolean) {
        updateEmptyVisibility(empty)
        binding.listEmptyAction.isVisible = empty
        binding.listEmptyAction.setOnClickListener { viewModel.refresh() }

        binding.listEmptyAction.setText(R.string.feed_empty_loaded_action)
        binding.listEmptyDescription.setText(R.string.feed_empty_loaded_description)
        binding.listEmptyIcon.setImageResource(R.drawable.ic_connection_off)
    }

    private fun setSavedList(list: List<Post>) {
        updateSavedEmptyState(list.isEmpty())
        adapter.submitList(list)
    }

    private fun updateSavedEmptyState(empty: Boolean) {
        updateEmptyVisibility(empty)
        binding.listEmptyAction.isVisible = false

        binding.listEmptyDescription.setText(R.string.feed_empty_saved_description)
        binding.listEmptyIcon.setImageResource(R.drawable.ic_ist)
    }

    private fun updateEmptyVisibility(empty: Boolean) {
        binding.listEmptyDescription.isVisible = empty
        binding.listEmptyIcon.isVisible = empty
        binding.list.isVisible = !empty
    }

    override fun onPostClick(post: Post) {
        findNavController().navigate(MainFragmentDirections.navigateToPostDetail(post.id))
    }

    override fun onLikeClick(post: Post) {
        viewModel.updateStatePost(post)
    }
}
