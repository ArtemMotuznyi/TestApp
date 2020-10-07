package com.developerartemmotuznyi.pikabufeed.data.network.source

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.data.network.model.PostResponse

interface RemoteFeedDataSource {

    suspend fun getFeed(): ActionResult<List<PostResponse>>

    suspend fun getPost(id: Long): ActionResult<PostResponse>


}