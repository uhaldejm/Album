package com.uhaldejm.myalbum.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val userId: Int,
    @ColumnInfo val title: String
)