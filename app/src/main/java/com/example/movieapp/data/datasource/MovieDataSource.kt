package com.example.movieapp.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.data.pagingdatasource.MoviePagingDataSource
import com.example.movieapp.retrofit.MovieDao
import com.example.movieapp.util.Resource
import kotlinx.coroutines.flow.Flow

class MovieDataSource(private var mdao: MovieDao) {
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


    suspend fun showMovieDetails(id: String): Resource<MovieDetailsResponse> {
        return try {
            val response = mdao.showMovieDetails(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }

}