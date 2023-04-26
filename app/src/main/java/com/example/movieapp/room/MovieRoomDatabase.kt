package com.example.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetailsResponse

@Database(entities = [Movie::class, MovieDetailsResponse::class], version = 4)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieRoomDao(): MovieRoomDao
}
