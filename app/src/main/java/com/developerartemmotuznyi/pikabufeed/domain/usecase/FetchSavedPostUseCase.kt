package com.developerartemmotuznyi.pikabufeed.domain.usecase

import com.developerartemmotuznyi.pikabufeed.domain.repository.FeedRepository
import javax.inject.Inject

class FetchSavedPostUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {

    suspend operator fun invoke() = feedRepository.fetchSavedPost()

}