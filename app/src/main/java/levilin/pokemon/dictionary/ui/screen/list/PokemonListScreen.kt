package levilin.pokemon.dictionary.ui.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import levilin.pokemon.dictionary.ui.theme.RobotoCondensed
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.model.local.PokemonListEntry
import levilin.pokemon.dictionary.ui.screen.list.component.FavoriteButton
import levilin.pokemon.dictionary.ui.screen.list.component.SearchBar
import levilin.pokemon.dictionary.utility.LoadableAsyncImage
import levilin.pokemon.dictionary.viewmodel.list.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_international_pokemon_logo),
                contentDescription = "pokemon_logo"
            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                hint = stringResource(id = R.string.search_bar_placeholder),
                viewModel = viewModel
            )
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val searchList by viewModel.searchList.collectAsState()
    val endReached by viewModel.endReached.collectAsState()
    val loadError by viewModel.loadError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    val listToShow = if (isSearching) searchList else pokemonList

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (listToShow.size % 2 == 0) {
            listToShow.size / 2
        } else {
            listToShow.size / 2 + 1
        }
        items(itemCount) { index ->
            if (index >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                LaunchedEffect(true) {
                    viewModel.loadPokemonList()
                }
            }
            PokemonRow(rowIndex = index, entries = listToShow, navController = navController)
        }
    }

    if (!isSearching) {
        Box(
            contentAlignment = Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            } else {
                if (loadError.isNotEmpty()) {
                    RetrySection(error = loadError) {
                        viewModel.loadPokemonList()
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonEntry(
    modifier: Modifier = Modifier,
    entry: PokemonListEntry,
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    // Focus Control
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        contentAlignment = Center
    ) {
        Column {
            LoadableAsyncImage(
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.pokemonName,
                alignment = Center,
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
fun PokemonRow(
    rowIndex: Int,
    entries: List<PokemonListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokemonEntry(
                modifier = Modifier.weight(1f),
                entry = entries[rowIndex * 2],
                navController = navController
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PokemonEntry(
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

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.align(CenterHorizontally),
            onClick = { onRetry() }
        ) {
            Text(text = stringResource(id = R.string.retry_button))
        }
    }
}