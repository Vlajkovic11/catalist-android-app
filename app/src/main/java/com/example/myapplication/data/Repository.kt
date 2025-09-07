package com.example.myapplication.data

interface Repository {
    suspend fun getAllCatBreeds(): List<Breed>
    suspend fun getCatBreedById(id: String): Breed
    suspend fun searchCatBreeds(query: String): List<Breed>
    suspend fun getBreedImageByID(breedId: String): Search?
}