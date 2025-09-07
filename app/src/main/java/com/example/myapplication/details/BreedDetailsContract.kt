package com.example.myapplication.details

import com.example.myapplication.data.BreedM

interface BreedDetailsContract {
    data class UiState(
        val isLoading: Boolean = false,
        val breed: BreedM? = null,
        val imageUrl: String? = null,
        val isRare: Boolean = false,
        val error: String? = null
    )
    sealed class UiEvent {
        data class LoadBreed(val breedId: String) : UiEvent()
        data class OpenWikipedia(val url: String) : UiEvent()
    }

    sealed class SideEffect {
        data class OpenWikipedia(val url: String) : SideEffect()
    }
}