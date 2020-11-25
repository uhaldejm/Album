package com.uhaldejm.myalbum.injection

import android.content.Context
import com.squareup.picasso.Downloader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object PicassoModule {

    @Singleton
    @Provides
    fun providePicasso(@ApplicationContext appContext: Context, downloader: Downloader): Picasso {
        return Picasso.Builder(appContext).build()
    }

    @Singleton
    @Provides
    fun provideDownloader(okHttpClient: OkHttpClient): Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

}