package com.developerartemmotuznyi.pikabufeed.data.di

import com.developerartemmotuznyi.pikabufeed.data.local.source.LocalFeedDataSource
import com.developerartemmotuznyi.pikabufeed.data.local.source.RoomFeedDataSource
import com.developerartemmotuznyi.pikabufeed.data.network.source.RemoteFeedDataSource
import com.developerartemmotuznyi.pikabufeed.data.network.source.RetrofitFeedDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalPostDataSource(selectedProductsDataSource: RoomFeedDataSource): LocalFeedDataSource

    @Binds
    abstract fun bindRemotePostDataSource(selectedProductsDataSource: RetrofitFeedDataSource): RemoteFeedDataSource

}