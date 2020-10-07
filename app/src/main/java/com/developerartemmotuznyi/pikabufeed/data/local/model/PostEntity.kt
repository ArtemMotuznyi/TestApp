package com.developerartemmotuznyi.pikabufeed.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developerartemmotuznyi.pikabufeed.domain.model.Post

@Entity
data class PostEntity(
    @PrimaryKey val id: Long,
    val title: String?,
    val body: String?,
    val images: String?
) {

    companion object {
        fun create(post: Post) = PostEntity(
            post.id,
            post.title,
            post.body,
            post.images.joinToString(",")
        )
    }

}

fun PostEntity.toDomain() = Post(
    id,
    title.orEmpty(),
    body.orEmpty(),
    images?.split(",").orEmpty(),
    true
)
