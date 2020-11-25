package com.uhaldejm.myalbum.injection

import com.uhaldejm.myalbum.data.local.daos.PhotoDao
import com.uhaldejm.myalbum.data.remote.api.MyAlbumsAPI
import com.uhaldejm.myalbum.data.repositories.definitions.IPhotosRepository
import com.uhaldejm.myalbum.data.repositories.implementations.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PhotoRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provideAlbumRepository(albumsApi: MyAlbumsAPI, photosDao: PhotoDao)
            : IPhotosRepository {
        return PhotoRepository(albumsApi, photosDao)
    }

}