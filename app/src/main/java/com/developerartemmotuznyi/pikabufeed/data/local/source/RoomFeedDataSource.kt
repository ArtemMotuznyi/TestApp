package com.developerartemmotuznyi.pikabufeed.data.local.source

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.data.local.base.execute
import com.developerartemmotuznyi.pikabufeed.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomFeedDataSource @Inject constructor(
    private val postsDao: PostsDao
) : LocalFeedDataSource {

    override suspend fun fetchPosts(): Flow<List<PostEntity>> = postsDao.fetchPosts()

    override suspend fun savePost(postEntity: PostEntity): ActionResult<Unit> =
        postsDao.execute { insert(postEntity) }

    override suspend fun deletePost(postId: Long): ActionResult<Unit> =
        postsDao.execute { delete(postId) }

    override suspend fun loadPosts(): ActionResult<List<PostEntity>> =
        postsDao.execute { getAllPosts() }
}
