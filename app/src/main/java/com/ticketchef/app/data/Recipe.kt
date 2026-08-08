package com.ticketchef.app.data

data class Recipe(
    val id: String,
    val name: String,
    val category: String,
    val minutes: Int,
    val difficulty: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val emoji: String
)

data class MatchedRecipe(
    val recipe: Recipe,
    val matchedIngredients: List<String>,
    val missingIngredients: List<String>,
    val score: Float
)
