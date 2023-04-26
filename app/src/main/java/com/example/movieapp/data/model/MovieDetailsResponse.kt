package com.example.movieapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movieDetails")
data class MovieDetailsResponse(
    @SerializedName("Actors")
    val actors: String? = "",

    @SerializedName("BoxOffice")
    val boxOffice: String? = "",

    @SerializedName("Country")
    val country: String? = "",

    @SerializedName("Director")
    val director: String? = "",

    @SerializedName("Language")
    val language: String? = "",

    @SerializedName("Plot")
    val plot: String? = "",

    @SerializedName("Poster")
    val poster: String? = "",

    @SerializedName("Response")
    val response: String? = "",

    @SerializedName("Runtime")
    val runtime: String? = "",

    @SerializedName("Title")
    val title: String? = "",

    @SerializedName("Writer")
    val writer: String? = "",

    @SerializedName("Year")
    val year: String? = "",

    @PrimaryKey
    @SerializedName("imdbID")
    val id: String,

    @SerializedName("imdbRating")
    val rating: String? = "",

    @SerializedName("Error")
    val error: String? = ""
)