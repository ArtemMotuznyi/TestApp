package com.developerartemmotuznyi.pikabufeed.data.local.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developerartemmotuznyi.pikabufeed.data.local.base.BaseDao
import com.developerartemmotuznyi.pikabufeed.data.local.model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao : BaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(postEntity: PostEntity)

    @Query("DELETE FROM postentity WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM postentity")
    fun fetchPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM postentity")
    fun getAllPosts(): List<PostEntity>


}
