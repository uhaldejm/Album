package com.uhaldejm.myalbum.injection

import com.uhaldejm.myalbum.data.local.daos.AlbumDao
import com.uhaldejm.myalbum.data.remote.api.MyAlbumsAPI
import com.uhaldejm.myalbum.data.repositories.definitions.IAlbumsRepository
import com.uhaldejm.myalbum.data.repositories.implementations.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object AlbumRepositoryModule {

    @ActivityRetainedScoped
    @Provides
    fun provideAlbumRepository(albumsApi: MyAlbumsAPI, albumsDao: AlbumDao)
            : IAlbumsRepository {
        return AlbumRepository(albumsApi, albumsDao)
    }

}