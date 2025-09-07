package com.example.myapplication.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("breeds")
    suspend fun getAllCatBreeds(): List<Breed>

    @GET("breeds/{breed_id}")
    suspend fun getCatBreedById(@Path("breed_id") breedId: String): Breed

    @GET("breeds/search")
    suspend fun searchCatBreeds(@Query("q") query: String): List<Breed>

    @GET("images/search")
    suspend fun getBreedImageByID(@Query("breed_ids") breedId: String): List<Search>


}