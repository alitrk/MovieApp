package com.example.movieapp.error

import java.util.*

data class ConsumableError(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val exception: String
)