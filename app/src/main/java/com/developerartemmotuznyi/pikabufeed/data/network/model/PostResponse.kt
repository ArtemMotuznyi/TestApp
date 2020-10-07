package com.developerartemmotuznyi.pikabufeed.data.network.model

import com.developerartemmotuznyi.pikabufeed.domain.model.Post

data class PostResponse(
    val id: Long?,
    val title: String?,
    val body: String?,
    val images: List<String>?
)

fun PostResponse.toDomain(isSaved: Boolean) = Post(
    id ?: -1,
    title.orEmpty(),
    body.orEmpty(),
    images.orEmpty(),
    isSaved
)