package levilin.pokemon.dictionary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = HpColor,
    background = Black,
    onBackground = LightGray,
    surface = DarkGray,
    onSurface = MediumGray,
    onPrimary = DarkRed,
    onSecondary = DarkMint,
)

private val LightColorPalette = lightColors(
    primary = MediumGray,
    background = LightBlue,
    onBackground = Black,
    surface = LightGray,
    onSurface = MediumDarkGray,
    onPrimary = LightRed,
    onSecondary = LightMint,
)

@Composable
fun PokemonDictionaryTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}