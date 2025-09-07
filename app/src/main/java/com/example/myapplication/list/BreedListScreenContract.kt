package com.example.myapplication.list

import com.example.myapplication.data.BreedM

interface BreedListScreenContract {
    data class UiState(
        val isLoading: Boolean = false,
        val breeds: List<BreedM> = emptyList(),
        val error: String? = null
    )
    sealed class UiEvent {
        object LoadBreeds : UiEvent()
        object NavigateToSearch : UiEvent()
    }
    sealed class SideEffect {
        data class NavigateToDetails(val breedId: String) : SideEffect()
        object NavigateToSearch : SideEffect()
    }
}