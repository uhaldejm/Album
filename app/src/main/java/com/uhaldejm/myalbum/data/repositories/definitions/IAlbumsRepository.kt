package com.uhaldejm.myalbum.data.repositories.definitions

import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.flow.Flow

interface IAlbumsRepository {

    fun getAlbums(): Flow<Resource<List<AlbumEntity>>>

}