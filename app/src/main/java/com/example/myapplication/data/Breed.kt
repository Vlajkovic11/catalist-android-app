package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    val description: String,
    val origin: String,
    val temperament: String,
    val alt_names: String? = null,
    val life_span: String,
    val weight: Weight,
    val image: Image? = null,
    val adaptability: Int,
    val affection_level: Int,
    val child_friendly: Int,
    val dog_friendly: Int,
    val energy_level: Int,
    val grooming: Int,
    val health_issues: Int,
    val intelligence: Int,
    val shedding_level: Int,
    val social_needs: Int,
    val stranger_friendly: Int,
    val vocalisation: Int,
    val rare: Int,
    val wikipedia_url: String? = null
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)

@Serializable
data class Image(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)