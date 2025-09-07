package com.example.myapplication.data

data class BreedM(
    val id: String,
    val name: String,
    val description: String,
    val temperament: List<String>,
    val origin: String,
    val altNames: String?,
    val lifeSpan: String,
    val weight: String,
    val imageUrl: String?,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    val rare: Int,
    val wikipediaUrl: String?
)