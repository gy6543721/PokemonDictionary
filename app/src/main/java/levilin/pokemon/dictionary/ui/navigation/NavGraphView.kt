package levilin.pokemon.dictionary.ui.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import levilin.pokemon.dictionary.ui.screen.detail.PokemonDetailScreen
import levilin.pokemon.dictionary.ui.screen.favorite.FavoriteListScreen
import levilin.pokemon.dictionary.ui.screen.list.PokemonListScreen

@Composable
fun NavGraphView(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "pokemon_list_screen",
        modifier = modifier.systemBarsPadding()
    ) {
        composable(route = "pokemon_list_screen") {
            PokemonListScreen(navController = navController)
        }
        composable(route =  "favorite_list_screen") {
            FavoriteListScreen(navController = navController)
        }
        composable(
            route = "pokemon_detail_screen/{dominantColor}/{pokemonID}",
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonID") {
                    type = NavType.StringType
                }
            )
        ) {
            val dominantColor = remember {
                val color = it.arguments?.getInt("dominantColor")
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonID = remember {
                it.arguments?.getString("pokemonID")
            }
            PokemonDetailScreen(
                dominantColor = dominantColor,
                pokemonID = pokemonID ?: "1",
                navController = navController
            )
        }
    }
}