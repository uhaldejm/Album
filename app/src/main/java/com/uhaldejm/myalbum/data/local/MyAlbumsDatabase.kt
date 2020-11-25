package com.uhaldejm.myalbum.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uhaldejm.myalbum.data.local.daos.AlbumDao
import com.uhaldejm.myalbum.data.local.daos.PhotoDao
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity

@Database(entities = [AlbumEntity::class, PhotoEntity::class], version = 2)
abstract class MyAlbumsDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
}