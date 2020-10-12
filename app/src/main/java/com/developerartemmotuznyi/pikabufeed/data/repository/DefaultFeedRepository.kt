package com.developerartemmotuznyi.pikabufeed.data.repository

import com.developerartemmotuznyi.pikabufeed.core.ActionResult
import com.developerartemmotuznyi.pikabufeed.core.onError
import com.developerartemmotuznyi.pikabufeed.data.local.model.PostEntity
import com.developerartemmotuznyi.pikabufeed.data.local.model.toDomain
import com.developerartemmotuznyi.pikabufeed.data.local.source.LocalFeedDataSource
import com.developerartemmotuznyi.pikabufeed.data.network.model.PostResponse
import com.developerartemmotuznyi.pikabufeed.data.network.model.toDomain
import com.developerartemmotuznyi.pikabufeed.data.network.source.RemoteFeedDataSource
import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.domain.repository.FeedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultFeedRepository @Inject constructor(
    private val localFeedDataSource: LocalFeedDataSource,
    private val remoteFeedDataSource: RemoteFeedDataSource
) : FeedRepository {

    override suspend fun fetchSavedPost(): Flow<List<Post>> =
        localFeedDataSource.fetchPosts()
            .map { it.map { postEntity -> postEntity.toDomain() } }
            .flowOn(Dispatchers.IO)

    override suspend fun getPosts(): ActionResult<List<Post>> = withContext(Dispatchers.IO) {
        val remoteAsync = async { remoteFeedDataSource.getFeed() }
        val localAsync = async { localFeedDataSource.loadPosts() }

        remoteAsync.await().join(localAsync.await()) { remote, local ->
            remote.map { remoteItem -> mapRemoteItemToDomain(remoteItem, local) }
        }
    }

    private fun mapRemoteItemToDomain(remoteItem: PostResponse, local: List<PostEntity>): Post {
        return remoteItem.toDomain(local.find { localItem -> localItem.id == remoteItem.id } != null)
    }

    override suspend fun savePost(post: Post): ActionResult<Unit> = withContext(Dispatchers.IO) {
        localFeedDataSource.savePost(PostEntity.create(post))
    }

    override suspend fun removePost(post: Post): ActionResult<Unit> = withContext(Dispatchers.IO) {
        localFeedDataSource.deletePost(post.id)
    }

    override suspend fun getPost(id: Long): ActionResult<Post> = withContext(Dispatchers.IO) {
        localFeedDataSource.getPost(id)
            .transform { it.toDomain() }
            .onError { remoteFeedDataSource.getPost(id).transform { it.toDomain(false) } }
    }
}