package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName ("Title")
    val title: String,
    @SerializedName ("Year")
    val year: String,
    @SerializedName ("imdbID")
    val id: String,
    @SerializedName ("Type")
    val type: String,
    @SerializedName ("Poster")
    val posterPath: String
)