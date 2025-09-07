package com.example.myapplication.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.data.BreedM
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    breedId: String,
    viewModel: BreedDetailsViewModel = hiltViewModel(),
    onOpenWikipedia: (String) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.setEvent(BreedDetailsContract.UiEvent.LoadBreed(breedId))
        viewModel.effect.collectLatest {
            when (it) {
                is BreedDetailsContract.SideEffect.OpenWikipedia -> onOpenWikipedia(it.url)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Breed Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if(state.isLoading){
            LoadingScreen()
        }else if(state.error != null) {
            ErrorScreen(state.error)
        }else if(state.breed != null){
            BreedDetailsContent(
            breed = state.breed,
            imageUrl = state.imageUrl,
            isRare = state.isRare,
            onWikipediaClick = {
                state.breed.wikipediaUrl?.let { url ->
                    viewModel.setEvent(BreedDetailsContract.UiEvent.OpenWikipedia(url))
                }
            },
            modifier = Modifier.padding(padding)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BreedDetailsContent(
    breed: BreedM,
    imageUrl: String?,
    isRare: Boolean,
    onWikipediaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),

    ) {
        item {
            imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Breed image",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            Text(breed.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Text(breed.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(20.dp))

            Text("Origin = " + breed.origin, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Temperament", style = MaterialTheme.typography.titleMedium)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                breed.temperament.forEach {
                    AssistChip(onClick = {}, label = { Text(it) })
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Life Span = " + breed.lifeSpan, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Weight = " + breed.weight, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))


            Text("Breed Attributes", style = MaterialTheme.typography.titleLarge)
            AttributeStars("Adaptability", breed.adaptability)
            AttributeStars("Affection", breed.affectionLevel)
            AttributeStars("Child Friendly", breed.childFriendly)
            AttributeStars("Dog Friendly", breed.dogFriendly)
            AttributeStars("Energy", breed.energyLevel)
            AttributeStars("Grooming", breed.grooming)
            AttributeStars("Health Issues", breed.healthIssues)
            AttributeStars("Intelligence", breed.intelligence)
            AttributeStars("Shedding", breed.sheddingLevel)
            AttributeStars("Social Needs", breed.socialNeeds)
            AttributeStars("Stranger Friendly", breed.strangerFriendly)
            AttributeStars("Vocalisation", breed.vocalisation)
            Spacer(modifier = Modifier.height(20.dp))
            Text("Rarity - " + if(isRare) "Rare" else "Common", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onWikipediaClick,
            ) {
                Text("Open in Wikipedia")
            }
        }
    }
}


@Composable
fun AttributeStars(label: String, value: Int, max: Int = 5) {
    Column(Modifier.padding(vertical = 10.dp)) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Row {
            repeat(max) { i ->
                Icon(
                    imageVector = if (i < value) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (i < value) Color.Magenta else Color.Gray,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
        Text("$value / $max", style = MaterialTheme.typography.titleMedium)
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