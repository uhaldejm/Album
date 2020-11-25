package com.uhaldejm.myalbum.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = REPLACE)
    fun insertAlbums(albums: List<AlbumEntity>)

    @Query("SELECT * FROM albums")
    fun loadAlbums(): Flow<List<AlbumEntity>>

    @Query("DELETE FROM albums")
    fun clearAlbums()

    @Transaction
    fun setAlbums(albums: List<AlbumEntity>) {
        clearAlbums()
        insertAlbums(albums)
    }


}