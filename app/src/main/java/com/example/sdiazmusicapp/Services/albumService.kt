package com.example.sdiazmusicapp.Services

import com.example.sdiazmusicapp.Models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface albumService {

    @GET("api/albums")
    suspend fun getAlbums(): List<Album>

    @GET("api/albums/{id}")
    suspend fun getAlbum(@Path("id") id: String): Album
}