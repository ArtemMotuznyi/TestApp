package com.developerartemmotuznyi.pikabufeed.domain.di

import com.developerartemmotuznyi.pikabufeed.data.repository.DefaultFeedRepository
import com.developerartemmotuznyi.pikabufeed.domain.repository.FeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFeedRepository(defaultMainRepository: DefaultFeedRepository): FeedRepository

}