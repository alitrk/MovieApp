package com.example.movieapp.data.repo

import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MoviesResponse

class MovieRepository (var mds: MovieDataSource){

    suspend fun searchMovie(title: String): MoviesResponse = mds.searchMovie(title)
}