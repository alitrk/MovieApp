package com.example.movieapp.retrofit

import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.util.Consts.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDao {

    @GET("?")
    suspend fun searchMovie(
        @Query("s")Title: String,
        @Query("apikey") apikey: String = API_KEY): MoviesResponse
}