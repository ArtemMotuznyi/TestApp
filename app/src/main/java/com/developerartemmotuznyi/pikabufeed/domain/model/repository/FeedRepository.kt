package com.developerartemmotuznyi.pikabufeed.domain.model.repository

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    suspend fun fetchSavedPost(): Flow<List<Post>>

    suspend fun getPosts(): ActionResult<List<Post>>

    suspend fun savePost(post: Post): ActionResult<Unit>

    suspend fun removePost(post: Post): ActionResult<Unit>

}