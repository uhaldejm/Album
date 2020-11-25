package com.uhaldejm.myalbum.data.repositories.implementations

import com.uhaldejm.myalbum.data.local.daos.AlbumDao
import com.uhaldejm.myalbum.data.local.entities.AlbumEntity
import com.uhaldejm.myalbum.data.remote.api.MyAlbumsAPI
import com.uhaldejm.myalbum.data.repositories.definitions.IAlbumsRepository
import com.uhaldejm.myalbum.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumsApi: MyAlbumsAPI,
    private val albumsDao: AlbumDao
) : IAlbumsRepository {

    override fun getAlbums(): Flow<Resource<List<AlbumEntity>>> {
        return flow {
            try {
                emit(Resource.Loading<List<AlbumEntity>>())
                val firstVal = albumsDao.loadAlbums().first()
                if (firstVal.size > 0) {
                    emit(Resource.Success(firstVal))
                }
                refreshAlbums()
                albumsDao.loadAlbums().collect { emit(Resource.Success(it)) }
            } catch (e: Exception) {
                emit(Resource.Error<List<AlbumEntity>>(e.message ?: "", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun refreshAlbums() {
        delay(100)
        val albums: List<AlbumEntity> =
            albumsApi.getAlbums().map { AlbumEntity(it.id, it.userId, it.title) }
        albumsDao.setAlbums(albums)
    }

}