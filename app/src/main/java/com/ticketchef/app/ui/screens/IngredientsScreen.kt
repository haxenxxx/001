package com.ticketchef.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ticketchef.app.data.IngredientDictionary
import com.ticketchef.app.viewmodel.AppViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IngredientsScreen(viewModel: AppViewModel, onSeeRecipes: () -> Unit) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }

    val suggestions = remember(query, viewModel.ingredients.toList()) {
        if (query.isBlank()) emptyList()
        else IngredientDictionary.allCanonicalIngredients()
            .filter { it !in viewModel.ingredients && it.contains(query.trim(), ignoreCase = true) }
            .take(6)
    }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                text = "Tus ingredientes",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = if (viewModel.ingredients.isEmpty())
                    "No hemos detectado ingredientes en el ticket. Anadelos a mano abajo."
                else
                    "Revisa lo que hemos leido del ticket. Quita lo que sobre o anade lo que falte.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))

            if (viewModel.ingredients.isEmpty()) {
                Text(
                    text = "Aun no hay ingredientes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.ingredients.forEach { ingredient ->
                        InputChip(
                            selected = false,
                            onClick = { viewModel.removeIngredient(ingredient) },
                            label = { Text(ingredient.replaceFirstChar { it.uppercase() }) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Quitar $ingredient",
                                    modifier = Modifier.height(16.dp)
                                )
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Anadir ingrediente", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej. pimiento, arroz, pollo...") },
                singleLine = true
            )

            if (suggestions.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(suggestions) { suggestion ->
                        TextButton(onClick = {
                            viewModel.addIngredient(suggestion)
                            query = ""
                        }) {
                            Text("+ " + suggestion.replaceFirstChar { it.uppercase() })
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.computeMatches(context)
                    onSeeRecipes()
                },
                enabled = viewModel.ingredients.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver recetas", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
