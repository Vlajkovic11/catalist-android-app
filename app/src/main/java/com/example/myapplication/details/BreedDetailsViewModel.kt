package com.example.myapplication.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Repository
import com.example.myapplication.data.toBreedM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(BreedDetailsContract.UiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: BreedDetailsContract.UiState.() -> BreedDetailsContract.UiState) =
        _state.getAndUpdate(reducer)

    private val _effect = Channel<BreedDetailsContract.SideEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()


    private val events = MutableSharedFlow<BreedDetailsContract.UiEvent>()
    fun setEvent(event: BreedDetailsContract.UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
    }

    private fun observeEvents() = viewModelScope.launch {
        events.collect { event ->
            when (event) {
                is BreedDetailsContract.UiEvent.LoadBreed -> loadBreedDetails(event.breedId)
                is BreedDetailsContract.UiEvent.OpenWikipedia -> openWikipedia(event.url)
            }
        }
    }

    private fun loadBreedDetails(breedId: String) = viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
        try {
            val breed = repository.getCatBreedById(breedId).toBreedM()
            val image = repository.getBreedImageByID(breedId)?.url
            setState {
                copy(
                    isLoading = false,
                    breed = breed,
                    imageUrl = image,
                    isRare = breed.rare == 1
                )
            }
        } catch (e: Exception) {
            setState { copy(isLoading = false, error = e.localizedMessage ?: "Unexpected error") }
        }
    }

    private fun openWikipedia(url: String) = viewModelScope.launch {
        _effect.send(BreedDetailsContract.SideEffect.OpenWikipedia(url))
    }
}