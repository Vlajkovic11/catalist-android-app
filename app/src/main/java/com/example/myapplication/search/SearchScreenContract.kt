package com.example.myapplication.search

import com.example.myapplication.data.BreedM

interface SearchScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        val results: List<BreedM> = emptyList(),
        val error: String? = null,
        val searchQuery: String = ""
    )

    sealed class UiEvent {
        data class Search(val query: String) : UiEvent()
        object ClearSearch : UiEvent()
    }

    sealed class SideEffect {
        data class NavigateToDetails(val breedId: String) : SideEffect()
    }
}