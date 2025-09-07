package com.example.myapplication.search

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
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenContract.UiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: SearchScreenContract.UiState.() -> SearchScreenContract.UiState) =
        _state.getAndUpdate(reducer)

    private val _effect = Channel<SearchScreenContract.SideEffect>()
    val effect = _effect.receiveAsFlow()

    private val events = MutableSharedFlow<SearchScreenContract.UiEvent>()
    fun setEvent(event: SearchScreenContract.UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
    }

    private fun observeEvents() = viewModelScope.launch {
        events.collect { event ->
            when (event) {
                is SearchScreenContract.UiEvent.Search -> searchBreeds(event.query)
                SearchScreenContract.UiEvent.ClearSearch -> clearSearch()
            }
        }
    }

    private fun searchBreeds(query: String) = viewModelScope.launch {
        setState { copy(isLoading = true, error = null, searchQuery = query)
        }
        try {
            val result = repository.searchCatBreeds(query).map { it.toBreedM() }
            setState{ copy(isLoading = false, results = result)
            }
        } catch (e: Exception) {
            setState { copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Search failed"
                )
            }
        }
    }

    private fun clearSearch() {
        setState { copy(results = emptyList(), searchQuery = "")
        }
    }

    fun onBreedSelected(breedId: String) {
        viewModelScope.launch {
            _effect.send(SearchScreenContract.SideEffect.NavigateToDetails(breedId))
        }
    }
}