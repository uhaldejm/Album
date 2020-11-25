package com.uhaldejm.myalbum.injection

import com.uhaldejm.myalbum.data.remote.api.MyAlbumsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MyAlbumsApiModule {

    @Singleton
    @Provides
    fun provideMyAlbumApi(): MyAlbumsAPI {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAlbumsAPI::class.java)
    }

}