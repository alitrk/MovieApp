package com.example.movieapp.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.movieapp.data.datasource.MovieDataSource
import com.example.movieapp.data.datasource.MovieRoomDatasource
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.util.Resource
import kotlinx.coroutines.flow.Flow

class MovieRepository(private var mds: MovieDataSource, private var movieRoomDataSource: MovieRoomDatasource) {

    fun searchMovie(title: String): Flow<PagingData<Movie>> = mds.searchMovie(title)

    suspend fun showMovieDetails(id: String): Resource<MovieDetailsResponse> = mds.showMovieDetails(id)

    //Room
    suspend fun insertMovie(movie: Movie) = movieRoomDataSource.insertMovie(movie)

    suspend fun deleteMovie(movie: Movie) = movieRoomDataSource.deleteMovie(movie)

    fun getMovies(): LiveData<List<Movie>> = movieRoomDataSource.observeMovies()

    //Room Details
    suspend fun insertMovieDetails(movieDetailsResponse: MovieDetailsResponse) =
        movieRoomDataSource.insertMovieDetails(movieDetailsResponse)

    suspend fun deleteMovieDetails(movieDetailsResponse: MovieDetailsResponse) =
        movieRoomDataSource.deleteMovieDetails(movieDetailsResponse)

    suspend fun deleteMovieByID(id: String) = movieRoomDataSource.deleteMovieByID(id)

    suspend fun observeMovieDetails(id: String): MovieDetailsResponse = movieRoomDataSource.observeMovieDetails(id)


}