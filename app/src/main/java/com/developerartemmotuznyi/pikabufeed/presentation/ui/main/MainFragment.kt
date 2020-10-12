package com.developerartemmotuznyi.pikabufeed.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.developerartemmotuznyi.pikabufeed.R
import com.developerartemmotuznyi.pikabufeed.databinding.FragmentMainBinding
import com.developerartemmotuznyi.pikabufeed.presentation.base.BaseViewModelFragment
import com.developerartemmotuznyi.pikabufeed.presentation.ui.feed.FeedFragment
import com.developerartemmotuznyi.pikabufeed.presentation.ui.feed.FeedType
import com.developerartemmotuznyi.pikabufeed.presentation.ui.feed.FeedViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseViewModelFragment<FragmentMainBinding, FeedViewModel>() {

    override val viewModel: FeedViewModel by viewModels()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf<Pair<Int, Fragment>>(
            R.string.feed_title to FeedFragment.newInstance(FeedType.Common),
            R.string.saved_post_title to FeedFragment.newInstance(FeedType.Saved)
        )

        val adapter = FragmentsAdapter(this, fragments)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = getString(adapter.getTitleRes(position))
        }.attach()
    }

    override fun initSubscription() {}
}

class FragmentsAdapter(
    fragment: Fragment,
    private val fragments: List<Pair<Int, Fragment>>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].second

    fun getTitleRes(position: Int): Int = fragments[position].first


}