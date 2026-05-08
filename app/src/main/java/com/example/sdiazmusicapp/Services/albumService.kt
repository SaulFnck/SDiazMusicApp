package com.example.sdiazmusicapp.Services

import retrofit2.http.GET
import retrofit2.http.Path

interface songsService {

    @GET("songs")
    suspend fun getAllProducts() : List<Product>

    //Path( /1 ), queryString ( ?name=Juan ) y Body ( {"name":"Juan"} )
    @GET("products/{asasas}")
    suspend fun getProductById(@Path("asasas") id: Int) : Product

}