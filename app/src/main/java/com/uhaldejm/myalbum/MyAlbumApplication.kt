package com.uhaldejm.myalbum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyAlbumApplication : Application() {

    @Override
    public override fun onCreate() {
        super.onCreate()
    }
}