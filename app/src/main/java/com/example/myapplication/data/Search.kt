package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed> = emptyList()
)
