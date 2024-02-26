package levilin.pokemon.dictionary.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import levilin.pokemon.dictionary.R

sealed class BottomNavItem(var titleStringID: Int, var icon: ImageVector, var route: String){
    data object PokemonList : BottomNavItem(titleStringID = R.string.nav_item_pokemon_list, icon = Icons.AutoMirrored.Filled.List, route = "pokemon_list_screen")
    data object FavoriteList : BottomNavItem(titleStringID = R.string.nav_item_favorite_list, icon = Icons.Default.Favorite, route = "favorite_list_screen")
}