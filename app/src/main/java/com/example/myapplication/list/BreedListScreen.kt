package com.example.myapplication.list


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.myapplication.data.BreedM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    viewModel: BreedListViewModel = hiltViewModel(),
    onBreedClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is BreedListScreenContract.SideEffect.NavigateToDetails -> onBreedClick(it.breedId)
                BreedListScreenContract.SideEffect.NavigateToSearch -> onSearchClick()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cat Breeds") },
                actions = {
                    IconButton(onClick = {
                        viewModel.setEvent(BreedListScreenContract.UiEvent.NavigateToSearch)
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if(state.isLoading) {
                LoadingScreen()
            }else if(state.error != null){
                ErrorScreen(state.error)
            }else {
                BreedList(breeds = state.breeds, onClick = { id ->
                    viewModel.onBreedSelected(id)
                })
            }
        }
    }
}


@Composable
private fun BreedList(
    breeds: List<BreedM>,
    onClick: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding = PaddingValues(bottom = 25.dp)
    ) {
        items(breeds) { breed ->
            BreedCard(
                breed = breed,
                onClick = { onClick(breed.id) }
            )
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}