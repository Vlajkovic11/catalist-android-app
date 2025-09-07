package com.example.myapplication.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.BreedM

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BreedCard(
    breed: BreedM,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .clickable { onClick() },
        colors = CardDefaults.outlinedCardColors(Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = breed.name,
                style = MaterialTheme.typography.headlineLarge
            )

            if (!breed.altNames.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Also known as: ${breed.altNames}",
                    style = MaterialTheme.typography.titleMedium

                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = if(breed.description.length > 250) breed.description.take(250) + "..." else breed.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(20.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                breed.temperament.take(5).forEach {
                    AssistChip(
                        onClick = {},
                        label = { Text(it) },
                        colors = AssistChipDefaults.assistChipColors(Color.Cyan)
                    )
                }
            }
        }
    }
}