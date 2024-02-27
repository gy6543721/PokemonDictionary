package levilin.pokemon.dictionary.ui.screen.list.component

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import levilin.pokemon.dictionary.data.model.PokemonListEntry
import levilin.pokemon.dictionary.ui.theme.favouriteButtonColor
import levilin.pokemon.dictionary.ui.screen.list.PokemonListViewModel

@Composable
fun FavoriteButton(modifier: Modifier = Modifier, entry: PokemonListEntry, viewModel: PokemonListViewModel) {
    var isFavorite by remember { mutableStateOf(false) }
    isFavorite = viewModel.checkFavorite(input = entry)

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            isFavorite = !isFavorite
            viewModel.changeFavorite(isFavorite = isFavorite, entry = entry)
            viewModel.loadFavoriteList()
        }
    ) {
        Icon(
            modifier = modifier.graphicsLayer {
                scaleX = 1.1f
                scaleY = 1.1f
            },
            tint = if (isFavorite) {
                Color.Red
            } else {
                MaterialTheme.colors.favouriteButtonColor
            },
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "favorite_icon"
        )
    }
}