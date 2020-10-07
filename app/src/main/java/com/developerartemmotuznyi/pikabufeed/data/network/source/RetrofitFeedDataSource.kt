package com.developerartemmotuznyi.pikabufeed.data.network.source

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.data.network.base.execute
import com.developerartemmotuznyi.pikabufeed.data.network.model.PostResponse
import javax.inject.Inject


class RetrofitFeedDataSource @Inject constructor(
    private val api: FeedApi
) : RemoteFeedDataSource {

    override suspend fun getFeed(): ActionResult<List<PostResponse>> =
        api.execute { getFeed() }

    override suspend fun getPost(id: Long): ActionResult<PostResponse> =
        api.execute { getPost(id) }
}
