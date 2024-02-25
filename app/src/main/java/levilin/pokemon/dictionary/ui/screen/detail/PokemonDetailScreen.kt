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
import levilin.pokemon.dictionary.data.model.PokemonDetail
import levilin.pokemon.dictionary.data.remote.response.pokemon.Pokemon
import levilin.pokemon.dictionary.data.remote.response.pokemon.Type
import levilin.pokemon.dictionary.ui.screen.detail.component.TypeLocalizedText
import levilin.pokemon.dictionary.utility.LoadableAsyncImage
import levilin.pokemon.dictionary.ui.theme.DarkGray
import levilin.pokemon.dictionary.ui.theme.LightGray
import levilin.pokemon.dictionary.utility.AdjustableText
import levilin.pokemon.dictionary.utility.Resource
import levilin.pokemon.dictionary.utility.parseStatToAbbr
import levilin.pokemon.dictionary.utility.parseStatToColor
import levilin.pokemon.dictionary.utility.parseTypeToColor
import levilin.pokemon.dictionary.viewmodel.detail.PokemonDetailViewModel
import java.util.*
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonID: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(pokemonID) {
        viewModel.loadPokemonDetail(pokemonID)
    }

    val pokemonDetail = viewModel.pokemonDetail.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        when (pokemonDetail) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Resource.Success -> {
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

            is Resource.Error -> {
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
    pokemonDetail: Resource<PokemonDetail>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (pokemonDetail) {
        is Resource.Success -> {
            pokemonDetail.data?.let { detail ->
                PokemonDetailSection(
                    pokemonDetail = detail,
                    modifier = modifier.offset(y = (-20).dp)
                )
            }
        }

        is Resource.Error -> {
            Text(
                text = pokemonDetail.message ?: "Error",
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    pokemonDetail: PokemonDetail,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(state = scrollState)
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
        PokemonBaseStatus(pokemonInfo = pokemonDetail.pokemonInfo)
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
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
    sectionHeight: Dp = 80.dp
) {
    val pokemonWeightInKg = remember {
        round(x = pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember {
        round(x = pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "kg",
            dataIcon = painterResource(id = R.drawable.ic_weight),
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
                .background(Color.LightGray)
        )
        PokemonDetailDataItem(
            dataValue = pokemonHeightInMeters,
            dataUnit = "m",
            dataIcon = painterResource(id = R.drawable.ic_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(painter = dataIcon, contentDescription = null, tint = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue $dataUnit",
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun PokemonStatus(
    statusName: String,
    statusValue: Int,
    statusMaxValue: Int,
    statusColor: Color,
    height: Dp = 28.dp,
    animationDuration: Int = 1000,
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
            text = statusName,
            fontWeight = FontWeight.Bold,
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
                        DarkGray
                    } else {
                        LightGray
                    }
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(currentPercent.value)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(horizontal = 8.dp)
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
    pokemonInfo: Pokemon,
    animationDelay: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.base_status),
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val status = pokemonInfo.stats[i]
            PokemonStatus(
                statusName = parseStatToAbbr(status),
                statusValue = status.baseStat,
                statusMaxValue = maxBaseStat,
                statusColor = parseStatToColor(status),
                animationDelay = i * animationDelay
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}