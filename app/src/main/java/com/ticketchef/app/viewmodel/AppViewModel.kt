package com.ticketchef.app.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ticketchef.app.data.MatchedRecipe
import com.ticketchef.app.data.Recipe
import com.ticketchef.app.data.RecipeRepository
import com.ticketchef.app.ocr.IngredientParser
import com.ticketchef.app.recipes.RecipeMatcher

class AppViewModel : ViewModel() {

    var captureTrigger by mutableStateOf(0)
        private set

    var isProcessingOcr by mutableStateOf(false)
        private set

    var ocrError by mutableStateOf<String?>(null)
        private set

    val ingredients: SnapshotStateList<String> = mutableStateListOf()

    var matches by mutableStateOf<List<MatchedRecipe>>(emptyList())
        private set

    var selectedRecipe by mutableStateOf<Recipe?>(null)
        private set

    fun onCaptureStarted() {
        isProcessingOcr = true
        ocrError = null
    }

    fun onOcrSuccess(rawText: String) {
        captureTrigger++
        isProcessingOcr = false
        val parsed = IngredientParser.parse(rawText)
        parsed.forEach { id -> if (id !in ingredients) ingredients.add(id) }
    }

    fun onOcrFailure(message: String) {
        isProcessingOcr = false
        ocrError = message
    }

    fun addIngredient(id: String) {
        if (id !in ingredients) ingredients.add(id)
    }

    fun removeIngredient(id: String) {
        ingredients.remove(id)
    }

    fun computeMatches(context: Context) {
        val recipes = RecipeRepository.loadAll(context)
        matches = RecipeMatcher.match(recipes, ingredients.toSet())
    }

    fun selectRecipe(recipe: Recipe) {
        selectedRecipe = recipe
    }
}
