package com.uhaldejm.myalbum.data.remote.api

import com.uhaldejm.myalbum.data.remote.response.AlbumResponse
import com.uhaldejm.myalbum.data.remote.response.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MyAlbumsAPI {

    @GET("/albums")
    suspend fun getAlbums(): List<AlbumResponse>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<PhotoResponse>

}