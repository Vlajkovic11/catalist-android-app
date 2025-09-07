package com.example.myapplication.list

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
class BreedListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(BreedListScreenContract.UiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: BreedListScreenContract.UiState.() -> BreedListScreenContract.UiState) =
        _state.getAndUpdate(reducer)

    private val _effect = Channel<BreedListScreenContract.SideEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val events = MutableSharedFlow<BreedListScreenContract.UiEvent>()
    fun setEvent(event: BreedListScreenContract.UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        setEvent(BreedListScreenContract.UiEvent.LoadBreeds)
    }

    private fun observeEvents() = viewModelScope.launch {
        events.collect { event ->
            when (event) {
                is BreedListScreenContract.UiEvent.LoadBreeds -> loadBreeds()
                is BreedListScreenContract.UiEvent.NavigateToSearch -> _effect.send(BreedListScreenContract.SideEffect.NavigateToSearch)
            }
        }
    }

    private fun loadBreeds() = viewModelScope.launch {
        setState { copy(isLoading = true, error = null) }
        try {
            val breeds = repository.getAllCatBreeds().map { it.toBreedM() }
            setState { copy(isLoading = false, breeds = breeds, error = null) }
        } catch (e: Exception) {
            setState { copy(isLoading = false, error = e.localizedMessage ?: "Unexpected error") }
        }
    }

    fun onBreedSelected(breedId: String) {
        viewModelScope.launch {
            _effect.send(BreedListScreenContract.SideEffect.NavigateToDetails(breedId))
        }
    }
}