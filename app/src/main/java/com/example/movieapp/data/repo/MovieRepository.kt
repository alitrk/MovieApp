package com.example.movieapp.data.repo

import androidx.paging.PagingData
import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.util.Resource
import kotlinx.coroutines.flow.Flow

class MovieRepository (var mds: MovieDataSource){

    fun searchMovie(title: String): Flow<PagingData<Movie>> = mds.searchMovie(title)

    suspend fun showMovieDetails(id: String): Resource<MovieDetailsResponse> = mds.showMovieDetails(id)
}