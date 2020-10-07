package com.developerartemmotuznyi.pikabufeed.data.di

import android.content.Context
import androidx.room.Room
import com.developerartemmotuznyi.pikabufeed.data.local.PikabuDatabase
import com.developerartemmotuznyi.pikabufeed.data.local.source.PostsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object LocalDataModule {

    @Provides
    fun provideDataBase(@ApplicationContext applicationContext: Context): PikabuDatabase =
        Room.databaseBuilder(applicationContext, PikabuDatabase::class.java, "database-pikabu")
            .build()

    @Provides
    fun providePostsDao(roomDatabase: PikabuDatabase): PostsDao = roomDatabase.postsDao()

}