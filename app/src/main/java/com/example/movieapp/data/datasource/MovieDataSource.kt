package com.example.movieapp.data.datasource

import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.retrofit.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.EOFException

class MovieDataSource (var mdao: MovieDao){
    suspend fun searchMovie(title: String): MoviesResponse =
        withContext(Dispatchers.IO){
            mdao.searchMovie(title)
        }
}