package com.uhaldejm.myalbum.data.repositories.implementations

import com.uhaldejm.myalbum.data.local.daos.PhotoDao
import com.uhaldejm.myalbum.data.local.entities.PhotoEntity
import com.uhaldejm.myalbum.data.remote.api.MyAlbumsAPI
import com.uhaldejm.myalbum.data.repositories.definitions.IPhotosRepository
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photosApi: MyAlbumsAPI,
    private val photosDao: PhotoDao
) : IPhotosRepository {

    override fun getAlbumPhotos(albumid: Int): Flow<Resource<List<PhotoEntity>>> {
        return flow {
            try {
                emit(Resource.Loading<List<PhotoEntity>>())
                val firstVal = photosDao.loadAlbumPhotos(albumid).first()
                if (firstVal.size > 0) {
                    emit(Resource.Success(firstVal))
                }
                refreshAlbumPhotos(albumid)
                photosDao.loadAlbumPhotos(albumid).collect { emit(Resource.Success(it)) }
            } catch (e: Exception) {
                emit(Resource.Error<List<PhotoEntity>>(e.message ?: "", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun refreshAlbumPhotos(albumid: Int) {
        val photos: List<PhotoEntity> = photosApi.getPhotos(albumid)
            .map { PhotoEntity(it.id, it.albumId, it.title, it.url, it.thumbnailUrl) }
        photosDao.setAlbumPhotos(albumid, photos)
    }

}