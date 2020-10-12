package com.developerartemmotuznyi.pikabufeed.data.local.source

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

interface LocalFeedDataSource {

    suspend fun fetchPosts(): Flow<List<PostEntity>>

    suspend fun savePost(postEntity: PostEntity): ActionResult<Unit>

    suspend fun deletePost(postId: Long): ActionResult<Unit>

    suspend fun loadPosts(): ActionResult<List<PostEntity>>

    suspend fun getPost(id: Long): ActionResult<PostEntity>

}
