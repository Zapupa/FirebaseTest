package com.example.firebasetest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    @SerialName("s_id")
    val id: Int,
    @SerialName("s_name")
    val name: String,
    @SerialName("s_author")
    val author: String
)
