package com.uhaldejm.myalbum.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = REPLACE)
    fun insertPhotos(albums: List<PhotoEntity>)

    @Query("SELECT * FROM photos WHERE albumId = :albumId")
    fun loadAlbumPhotos(albumId: Int): Flow<List<PhotoEntity>>

    @Query("DELETE FROM photos WHERE albumId = :albumId")
    fun clearAlbumPhotos(albumId: Int)

    @Transaction
    fun setAlbumPhotos(albumId: Int, photos: List<PhotoEntity>) {
        clearAlbumPhotos(albumId)
        insertPhotos(photos.map { PhotoEntity(it.id, albumId, it.title, it.url, it.thumbnailUrl) })
    }

}