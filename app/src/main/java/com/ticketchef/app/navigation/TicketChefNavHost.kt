package com.ticketchef.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ticketchef.app.ui.screens.IngredientsScreen
import com.ticketchef.app.ui.screens.RecipeDetailScreen
import com.ticketchef.app.ui.screens.ResultsScreen
import com.ticketchef.app.ui.screens.ScanScreen
import com.ticketchef.app.ui.screens.SplashScreen
import com.ticketchef.app.viewmodel.AppViewModel

private object Routes {
    const val SPLASH = "splash"
    const val SCAN = "scan"
    const val INGREDIENTS = "ingredients"
    const val RESULTS = "results"
    const val DETAIL = "detail"
}

@Composable
fun TicketChefNavHost(viewModel: AppViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onStart = { navController.navigate(Routes.SCAN) })
        }
        composable(Routes.SCAN) {
            ScanScreen(
                viewModel = viewModel,
                onIngredientsReady = { navController.navigate(Routes.INGREDIENTS) }
            )
        }
        composable(Routes.INGREDIENTS) {
            IngredientsScreen(
                viewModel = viewModel,
                onSeeRecipes = { navController.navigate(Routes.RESULTS) }
            )
        }
        composable(Routes.RESULTS) {
            ResultsScreen(
                viewModel = viewModel,
                onRecipeSelected = { navController.navigate(Routes.DETAIL) }
            )
        }
        composable(Routes.DETAIL) {
            RecipeDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
