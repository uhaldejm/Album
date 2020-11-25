package com.uhaldejm.myalbum.data.remote.response

data class PhotoResponse(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)