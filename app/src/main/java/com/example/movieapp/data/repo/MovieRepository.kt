package com.example.movieapp.data.repo

import androidx.paging.PagingData
import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.model.MoviesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieRepository (var mds: MovieDataSource){

    fun searchMovie(title: String): Flow<PagingData<Movie>> = mds.searchMovie(title)

    suspend fun showMovieDetails(id: String): MovieDetailsResponse = mds.showMovieDetails(id)
}