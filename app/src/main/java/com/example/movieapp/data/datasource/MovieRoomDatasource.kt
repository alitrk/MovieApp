package com.example.movieapp.data.datasource

import androidx.lifecycle.LiveData
import com.example.movieapp.data.model.Movie
import com.example.movieapp.room.MovieRoomDao

class MovieRoomDatasource(var movieRoomDao: MovieRoomDao) {

    suspend fun insertMovie(movie: Movie) {
        movieRoomDao.insertMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieRoomDao.deleteMovie(movie)
    }

    fun observeMovies(): LiveData<List<Movie>> {
        return movieRoomDao.observeMovies()
    }
}