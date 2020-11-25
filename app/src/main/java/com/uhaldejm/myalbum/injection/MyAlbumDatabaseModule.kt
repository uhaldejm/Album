package com.uhaldejm.myalbum.injection

import android.content.Context
import androidx.room.Room
import com.uhaldejm.myalbum.data.local.MyAlbumsDatabase
import com.uhaldejm.myalbum.data.local.daos.AlbumDao
import com.uhaldejm.myalbum.data.local.daos.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MyAlbumDatabaseModule {

    @Singleton
    @Provides
    fun provideMyAlbumsDatabase(@ApplicationContext appContext: Context): MyAlbumsDatabase {
        return Room.databaseBuilder(
            appContext,
            MyAlbumsDatabase::class.java, "my-albums-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(albumsDb: MyAlbumsDatabase): AlbumDao {
        return albumsDb.albumDao()
    }

    @Singleton
    @Provides
    fun providePhotoDao(albumsDb: MyAlbumsDatabase): PhotoDao {
        return albumsDb.photoDao()
    }
}