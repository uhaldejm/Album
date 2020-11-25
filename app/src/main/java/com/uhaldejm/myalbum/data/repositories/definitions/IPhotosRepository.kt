package com.uhaldejm.myalbum.data.repositories.definitions

import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.flow.Flow

interface IPhotosRepository {

    fun getAlbumPhotos(albumid: Int): Flow<Resource<List<PhotoEntity>>>

}