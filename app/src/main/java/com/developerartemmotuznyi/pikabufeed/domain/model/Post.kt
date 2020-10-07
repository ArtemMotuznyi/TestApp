package com.developerartemmotuznyi.pikabufeed.domain.model

data class Post(
    val id: Long,
    val title: String,
    val body: String,
    val images: List<String>,
    val isSaved: Boolean
)
