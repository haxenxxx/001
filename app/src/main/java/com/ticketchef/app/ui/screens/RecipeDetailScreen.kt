package com.ticketchef.app.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.item
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ticketchef.app.viewmodel.AppViewModel

@Composable
fun RecipeDetailScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    val recipe = viewModel.selectedRecipe ?: return
    val matched = viewModel.matches.find { it.recipe.id == recipe.id }
    val matchedSet = matched?.matchedIngredients?.toSet() ?: emptySet()

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            item {
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(text = recipe.emoji, fontSize = 56.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "${recipe.minutes} min · ${recipe.difficulty} · ${recipe.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(24.dp))
                Text(text = "Ingredientes", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
            }
            items(recipe.ingredients) { ingredient ->
                val isOwned = ingredient in matchedSet
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isOwned) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.width(20.dp)
                        )
                    } else {
                        Spacer(Modifier.width(20.dp))
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = ingredient.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isOwned) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            item {
                Spacer(Modifier.height(24.dp))
                Text(text = "Preparacion", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
            }
            itemsIndexed(recipe.steps) { index, step ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(28.dp)
                    )
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            item { Spacer(Modifier.height(40.dp)) }
        }
    }
}
