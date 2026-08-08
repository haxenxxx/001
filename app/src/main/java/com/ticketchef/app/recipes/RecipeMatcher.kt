package com.ticketchef.app.recipes

import com.ticketchef.app.data.MatchedRecipe
import com.ticketchef.app.data.Recipe

/**
 * Scores each recipe by the fraction of its ingredients present in the
 * pantry (ingredients detected on the ticket, plus any the user added by
 * hand). Recipes with zero matches are dropped.
 */
object RecipeMatcher {

    fun match(recipes: List<Recipe>, pantry: Set<String>): List<MatchedRecipe> {
        return recipes.mapNotNull { recipe ->
            val matched = recipe.ingredients.filter { it in pantry }
            if (matched.isEmpty()) return@mapNotNull null
            val missing = recipe.ingredients.filterNot { it in pantry }
            val score = matched.size.toFloat() / recipe.ingredients.size.toFloat()
            MatchedRecipe(recipe, matched, missing, score)
        }.sortedWith(compareByDescending<MatchedRecipe> { it.score }.thenBy { it.missingIngredients.size })
    }
}
