package levilin.pokemon.dictionary.ui.screen.favorite

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import levilin.pokemon.dictionary.ui.screen.list.PokemonList

@Composable
fun FavoriteListScreen(
    navController: NavController
) {
    Surface {
        PokemonList(navController = navController)
    }
}