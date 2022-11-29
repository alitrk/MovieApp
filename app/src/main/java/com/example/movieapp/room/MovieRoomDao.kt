package com.example.movieapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse

@Dao
interface MovieRoomDao {
    //Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movies")
    fun observeMovies(): LiveData<List<Movie>>

    //MovieDetails
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetailsResponse: MovieDetailsResponse)

    @Delete
    suspend fun deleteMovieDetails(movieDetailsResponse: MovieDetailsResponse)

    @Query("DELETE FROM movieDetails WHERE id =:id")
    suspend fun deleteMovieByID(id: String)

    @Query("SELECT * FROM movieDetails WHERE id =:id")
    suspend fun observeMovieDetails(id: String): MovieDetailsResponse
}