package com.example.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.model.Movie

@Database(entities = [Movie::class],version = 1)
abstract class MovieRoomDatabase: RoomDatabase() {
    abstract fun movieRoomDao() : MovieRoomDao
}
