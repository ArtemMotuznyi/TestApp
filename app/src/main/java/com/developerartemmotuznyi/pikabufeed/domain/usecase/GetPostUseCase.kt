package com.developerartemmotuznyi.pikabufeed.domain.usecase

import com.developerartemmotuznyi.pikabufeed.domain.repository.FeedRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val repository: FeedRepository
){

    suspend operator fun invoke(id: Long) = repository.getPost(id)

}