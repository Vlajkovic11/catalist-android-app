package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.details.BreedDetailsScreen
import com.example.myapplication.list.BreedListScreen
import com.example.myapplication.search.SearchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Route.BREEDS_LIST
    ) {
        breedsListScreen(route = Route.BREEDS_LIST, navController = navController)
        breedDetailsScreen(
            route = "${Route.BREED_DETAILS}/{${Route.ARG_BREED_ID}}",
            arguments = listOf(
                navArgument(Route.ARG_BREED_ID) { type = NavType.StringType }
            ),
            navController = navController
        )
        searchScreen(route = Route.SEARCH, navController = navController)
    }
}


private fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController
) = composable(route = route) {
    BreedListScreen (
        onBreedClick = { breedId ->
            navController.navigateToBreedDetails(breedId)
        },
        onSearchClick = {
            navController.navigate(Route.SEARCH)
        }
    )
}


private fun NavGraphBuilder.breedDetailsScreen(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController
) = composable(route = route, arguments = arguments) { backStackEntry ->
    val breedId = backStackEntry.arguments?.getString(Route.ARG_BREED_ID) ?: return@composable
    val uriHandler = LocalUriHandler.current

    BreedDetailsScreen(
        breedId = breedId,
        onOpenWikipedia = { url -> uriHandler.openUri(url) },
        onBack = { navController.navigateUp() }
    )
}


private fun NavGraphBuilder.searchScreen(
    route: String,
    navController: NavController
) = composable(route = route) {
    SearchScreen(
        onBreedClick = { breedId ->
            navController.navigateToBreedDetails(breedId)
        },
        onBack = { navController.navigateUp() }
    )
}


private fun NavController.navigateToBreedDetails(breedId: String) {
    this.navigate("${Route.BREED_DETAILS}/$breedId")
}


object Route {
    const val BREEDS_LIST = "breeds_list"
    const val BREED_DETAILS = "breed_details"
    const val ARG_BREED_ID = "breedId"
    const val SEARCH = "search"
}