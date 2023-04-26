package com.example.movieapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movies")
data class Movie(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Year")
    val year: String,

    @PrimaryKey
    @SerializedName("imdbID")
    val id: String,

    @SerializedName("Type")
    val type: String,

    @SerializedName("Poster")
    val posterPath: String
) : Serializable {

}
