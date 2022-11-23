package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("Search")
    val movieList: List<Movie> = listOf(),
    val totalResults: Int? = null,
    @SerializedName("Response")
    val response: String? = null,
    @SerializedName("Error")
    val error: String? = null
)