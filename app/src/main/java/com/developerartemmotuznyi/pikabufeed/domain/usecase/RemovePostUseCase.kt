package com.developerartemmotuznyi.pikabufeed.domain.usecase

import com.developerartemmotuznyi.pikabufeed.domain.model.Post
import com.developerartemmotuznyi.pikabufeed.domain.repository.FeedRepository
import javax.inject.Inject

class RemovePostUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {

    suspend operator fun invoke(post: Post) = feedRepository.removePost(post)

}