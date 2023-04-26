package com.example.movieapp.retrofit

import com.example.movieapp.util.Consts.BASE_URL

class ApiUtils {
    companion object {
        fun getMovieDao(): MovieDao {
            return RetrofitClient.getClient(BASE_URL).create(MovieDao::class.java)
        }
    }
}
