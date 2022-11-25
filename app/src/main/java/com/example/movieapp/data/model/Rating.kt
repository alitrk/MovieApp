package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Source")
    val source: String,

    @SerializedName ("Value")
    val value: String
)