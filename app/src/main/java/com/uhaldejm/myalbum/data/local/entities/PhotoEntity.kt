package com.uhaldejm.myalbum.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo
    val albumId: Int,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val url: String,
    @ColumnInfo
    val thumbnailUrl: String
)