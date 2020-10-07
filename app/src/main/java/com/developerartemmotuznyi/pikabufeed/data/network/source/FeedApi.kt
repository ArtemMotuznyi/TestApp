package com.developerartemmotuznyi.pikabufeed.data.network.source

import com.developerartemmotuznyi.pikabufeed.data.network.base.BaseApi
import com.developerartemmotuznyi.pikabufeed.data.network.model.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi : BaseApi {

    @GET("feed.php")
    suspend fun getFeed(): List<PostResponse>

    @GET("story.php")
    suspend fun getPost(@Query("id") id: Long): PostResponse

}
