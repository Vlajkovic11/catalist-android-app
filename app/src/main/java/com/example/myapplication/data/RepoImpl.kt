package com.example.myapplication.data

import javax.inject.Inject


class RepoImpl @Inject constructor(
    private val catApi: ApiService
): Repository {
    override suspend fun getAllCatBreeds(): List<Breed> {
        return catApi.getAllCatBreeds()
    }

    override suspend fun getCatBreedById(id: String): Breed {
        return catApi.getCatBreedById(id)
    }

    override suspend fun searchCatBreeds(query: String): List<Breed> {
        return catApi.searchCatBreeds(query)
    }
    override suspend fun getBreedImageByID(breedId: String): Search? {
        return catApi.getBreedImageByID(breedId).firstOrNull()
    }
}