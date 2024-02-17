package ru.sample.duckapp.domain

import com.google.gson.annotations.SerializedName

data class Duck(
    @SerializedName("url")
    val url: String,

    @SerializedName("message")
    val message: String,
)