package com.example.movieapp.data.datasource

import androidx.lifecycle.LiveData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse
import com.example.movieapp.room.MovieRoomDao

class MovieRoomDatasource(private var movieRoomDao: MovieRoomDao) {

    suspend fun insertMovie(movie: Movie) {
        movieRoomDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieRoomDao.deleteMovie(movie)
    }

    fun observeMovies(): LiveData<List<Movie>> {
        return movieRoomDao.observeMovies()
    }

    suspend fun insertMovieDetails(movieDetailsResponse: MovieDetailsResponse) {
        movieRoomDao.insertMovieDetails(movieDetailsResponse)
    }

    suspend fun deleteMovieDetails(movieDetailsResponse: MovieDetailsResponse) {
        movieRoomDao.deleteMovieDetails(movieDetailsResponse)
    }

    suspend fun observeMovieDetails(id: String): MovieDetailsResponse {
        return movieRoomDao.observeMovieDetails(id)
    }

    suspend fun deleteMovieByID(id: String) {
        movieRoomDao.deleteMovieByID(id)
    }

}