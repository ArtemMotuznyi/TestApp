package com.developerartemmotuznyi.pikabufeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerartemmotuznyi.pikabufeed.data.local.model.PostEntity
import com.developerartemmotuznyi.pikabufeed.data.local.source.PostsDao

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class PikabuDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}
