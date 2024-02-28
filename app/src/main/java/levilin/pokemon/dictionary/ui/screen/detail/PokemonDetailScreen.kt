package levilin.pokemon.dictionary.ui.screen.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.model.local.PokemonDetail
import levilin.pokemon.dictionary.ui.screen.detail.component.TypeLocalizedText
import levilin.pokemon.dictionary.utility.LoadableAsyncImage
import levilin.pokemon.dictionary.ui.theme.LightMediumGray
import levilin.pokemon.dictionary.ui.theme.MediumDarkGray
import levilin.pokemon.dictionary.utility.AdjustableText
import levilin.pokemon.dictionary.utility.NetworkResult
import levilin.pokemon.dictionary.utility.parseStatusToLocalizedStringID
import levilin.pokemon.dictionary.utility.parseStatusToColor
import levilin.pokemon.dictionary.utility.parseTypeToColor
import levilin.pokemon.dictionary.viewmodel.detail.PokemonDetailViewModel
import java.util.*
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonID: Int,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(pokemonID) {
        viewModel.loadPokemonDetail(pokemonID = pokemonID)
    }

    val pokemonDetail = viewModel.pokemonDetail.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        when (pokemonDetail) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is NetworkResult.Success -> {
                PokemonDetailTopSection(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .align(Alignment.TopCenter)
                )
                PokemonDetailStateWrapper(
                    pokemonDetail = pokemonDetail,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = topPadding + pokemonImageSize / 2f,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .shadow(10.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.surface)
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    loadingModifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                        .padding(
                            top = topPadding + pokemonImageSize / 2f,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    pokemonDetail.data?.pokemonInfo?.sprites.let { sprite ->
                        LoadableAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(sprite?.frontDefault)
                                .crossfade(true)
                                .build(),
                            contentDescription = pokemonDetail.data?.pokemonInfo?.name,
                            modifier = Modifier
                                .size(pokemonImageSize)
                                .offset(y = topPadding),
                            alignment = Alignment.Center
                        )
                    }
                }
            }

            is NetworkResult.Error -> {
                Text(
                    text = "Error: ${pokemonDetail.message}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokemonDetailStateWrapper(
    modifier: Modifier = Modifier,
    pokemonDetail: NetworkResult<PokemonDetail>,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonDetail) {
        is NetworkResult.Success -> {
            pokemonDetail.data?.let { detail ->
                PokemonDetailSection(
                    modifier = modifier.offset(y = (-20).dp),
                    pokemonDetail = detail
                )
            }
        }

        is NetworkResult.Error -> {
            Text(
                modifier = modifier,
                text = pokemonDetail.message ?: "Error",
                color = Color.Red
            )
        }

        is NetworkResult.Loading -> {
            CircularProgressIndicator(
                modifier = loadingModifier,
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    modifier: Modifier = Modifier,
    pokemonDetail: PokemonDetail
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .offset(y = 90.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${String.format("%03d", pokemonDetail.pokemonInfo.id)} ${
                pokemonDetail.pokemonSpecies.names.find { names ->
                    names.language.name.contains(Locale.getDefault().language)
                }?.name ?: pokemonDetail.pokemonInfo.name.replaceFirstChar { character ->
                    if (character.isLowerCase()) {
                        character.titlecase(Locale.ROOT)
                    } else {
                        character.toString()
                    }
                }
            }",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTypeSection(types = pokemonDetail.pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonDetail.pokemonInfo.weight,
            pokemonHeight = pokemonDetail.pokemonInfo.height
        )
        PokemonDescription(pokemonDetail = pokemonDetail)
        PokemonBaseStatus(pokemonDetail = pokemonDetail)
    }
}

@Composable
fun PokemonTypeSection(types: List<levilin.pokemon.dictionary.model.remote.pokemon.Type>) {
    Row(
        modifier = Modifier.padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (type in types) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                TypeLocalizedText(
                    text = type.type.name.lowercase()
                )
            }
        }
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 60.dp
) {
    val pokemonWeightInKg = remember {
        round(x = pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        round(x = pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        PokemonDetailDataItem(
            modifier = Modifier.weight(1f),
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_pokemon_weight)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            modifier = Modifier.weight(1f),
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_pokemon_height)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    modifier: Modifier = Modifier,
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue $dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonDescription(
    pokemonDetail: PokemonDetail
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 15.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = pokemonDetail.pokemonSpecies.flavorTextEntries.find { description ->
                description.language.name.contains(Locale.getDefault().language)
            }?.flavorText ?: stringResource(id = R.string.base_status),
            fontSize = 15.sp,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonStatus(
    statusNameStringID: Int,
    statusValue: Int,
    statusMaxValue: Int,
    statusColor: Color,
    height: Dp = 28.dp,
    animationDuration: Int = 500,
    animationDelay: Int = 0
) {
    var animationTriggered by remember {
        mutableStateOf(false)
    }
    val currentPercent = animateFloatAsState(
        targetValue = if (animationTriggered) {
            statusValue / statusMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animationDuration,
            animationDelay
        ),
        label = "current_percent_animation"
    )
    LaunchedEffect(key1 = true) {
        animationTriggered = true
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AdjustableText(
            modifier = Modifier.width(50.dp),
            text = stringResource(id = statusNameStringID),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        Spacer(modifier = Modifier.width(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(CircleShape)
                .background(
                    if (isSystemInDarkTheme()) {
                        MediumDarkGray
                    } else {
                        LightMediumGray
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(currentPercent.value)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AdjustableText(
                    text = (currentPercent.value * statusMaxValue).toInt().toString(),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}

@Composable
fun PokemonBaseStatus(
    pokemonDetail: PokemonDetail,
    animationDelay: Int = 100
) {
    val maxBaseStat = remember {
        pokemonDetail.pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in pokemonDetail.pokemonInfo.stats.indices) {
            val status = pokemonDetail.pokemonInfo.stats[i]
            PokemonStatus(
                statusNameStringID = parseStatusToLocalizedStringID(status),
                statusValue = status.baseStat,
                statusMaxValue = maxBaseStat,
                statusColor = parseStatusToColor(status),
                animationDelay = i * animationDelay
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}