package com.developerartemmotuznyi.pikabufeed.data.di

import com.developerartemmotuznyi.pikabufeed.data.network.source.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
object ApiModule {

    @Provides
    fun providePostApi(retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

}