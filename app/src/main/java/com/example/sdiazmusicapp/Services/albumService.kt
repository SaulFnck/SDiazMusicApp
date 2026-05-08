package com.example.sdiazmusicapp.Services

import com.example.sdiazmusicapp.Models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface albumService {

    @GET("albums")
    suspend fun getAllAlbums() : List<Album>

    //Path( /1 ), queryString ( ?name=Juan ) y Body ( {"name":"Juan"} )
    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int) : Album

}