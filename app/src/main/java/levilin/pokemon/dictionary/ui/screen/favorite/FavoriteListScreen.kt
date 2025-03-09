package levilin.pokemon.dictionary.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import levilin.pokemon.dictionary.ui.screen.list.PokemonListViewModel
import levilin.pokemon.dictionary.ui.screen.list.component.FavoriteButton
import levilin.pokemon.dictionary.ui.theme.RobotoCondensed
import levilin.pokemon.dictionary.utility.LoadableAsyncImage

@Composable
fun FavoriteListScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            FavoriteList(navController = navController)
        }
    }
}

@Composable
fun FavoriteList(
    navController: NavController,
    pokemonListViewModel: PokemonListViewModel = hiltViewModel()
) {
    val favoriteList by pokemonListViewModel.pokemonList.collectAsState().value
        .filter { it.isFavorite }
        .let { mutableStateOf(it) }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (favoriteList.size % 2 == 0) {
            favoriteList.size / 2
        } else {
            favoriteList.size / 2 + 1
        }
        items(itemCount) { index ->
            FavoriteRow(rowIndex = index, entries = favoriteList, navController = navController)
        }
    }
}

@Composable
fun FavoriteEntry(
    modifier: Modifier = Modifier,
    entry: PokemonListEntry,
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    route = "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.id}"
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column {
            LoadableAsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.pokemonName,
                alignment = Alignment.Center,
                onImageLoaded = { drawable ->
                    viewModel.getDominantColor(drawable) { color ->
                        dominantColor = color
                    }
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "${String.format("%03d", entry.id)} ${entry.pokemonName}",
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
        Box(
            // Align this box to the top end
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            FavoriteButton(entry = entry, viewModel = viewModel)
        }
    }
}

@Composable
fun FavoriteRow(
    rowIndex: Int,
    entries: List<PokemonListEntry>,
    navController: NavController
) {
    Column {
        Row {
            FavoriteEntry(
                modifier = Modifier.weight(1f),
                entry = entries[rowIndex * 2],
                navController = navController
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                FavoriteEntry(
                    modifier = Modifier.weight(1f),
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}