package com.example.movieapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.model.MoviesResponse
import com.example.movieapp.data.pagingdatasource.MoviePagingDataSource
import com.example.movieapp.retrofit.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.EOFException

class MovieDataSource (var mdao: MovieDao){
    fun searchMovie(title: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(mdao, title) }
        ).flow

    companion object {
        const val PAGE_SIZE = 10
    }

    suspend fun showMovieDetails(id:String): MovieDetailsResponse =
        withContext(Dispatchers.IO){
            mdao.showMovieDetails(id)
        }
}