package com.ticketchef.app.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object RecipeRepository {

    private var cached: List<Recipe>? = null

    fun loadAll(context: Context): List<Recipe> {
        cached?.let { return it }
        val json = context.assets.open("recipes.json").bufferedReader(Charsets.UTF_8).use { it.readText() }
        val array = JSONArray(json)
        val recipes = (0 until array.length()).map { i -> array.getJSONObject(i).toRecipe() }
        cached = recipes
        return recipes
    }

    private fun JSONObject.toRecipe(): Recipe {
        val ingredientsArray = getJSONArray("ingredients")
        val ingredients = (0 until ingredientsArray.length()).map { ingredientsArray.getString(it) }
        val stepsArray = getJSONArray("steps")
        val steps = (0 until stepsArray.length()).map { stepsArray.getString(it) }
        return Recipe(
            id = getString("id"),
            name = getString("name"),
            category = getString("category"),
            minutes = getInt("minutes"),
            difficulty = getString("difficulty"),
            ingredients = ingredients,
            steps = steps,
            emoji = getString("emoji")
        )
    }
}
