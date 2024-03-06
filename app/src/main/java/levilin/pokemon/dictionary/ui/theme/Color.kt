package levilin.pokemon.dictionary.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightRed = Color(0xFFF0B6B6)
val DarkRed = Color(0xFFFD8080)
val LightMint = Color(0xFFC3F0B6)
val DarkMint = Color(0xFF74CC5A)
val LightBlue = Color(0xFFBAC7FF)
val LightGray = Color(0xFFFAFAFA)
val LightMediumGray = Color(0xFFD1CFCF)
val MediumGray = Color(0xFFACA9A9)
val MediumDarkGray = Color(0xFF6F6C6C)
val DarkGray = Color(0xFF3D3C3C)
val Black = Color(0xFF101010)

val HpColor = Color(0xFFD3A124)
val AtkColor = Color(1f, 0f, 0f, 0.66f)
val DefColor = Color(0f, 0f, 1f, 0.44f)
val SpAtkColor = Color(0.671f, 0f, 1f, 0.57f)
val SpDefColor = Color(1f, 0f, 0.8f, 0.7f)
val SpdColor = Color(0f, 1f, 0.063f, 0.55f)

val TypeNormal = Color(0xFFA8A77A)
val TypeFire = Color(0xFFEE8130)
val TypeWater = Color(0xFF6390F0)
val TypeElectric = Color(0xFFF7D02C)
val TypeGrass = Color(0xFF7AC74C)
val TypeIce = Color(0xFF96D9D6)
val TypeFighting = Color(0xFFC22E28)
val TypePoison = Color(0xFFA33EA1)
val TypeGround = Color(0xFFE2BF65)
val TypeFlying = Color(0xFFA98FF3)
val TypePsychic = Color(0xFFF95587)
val TypeBug = Color(0xFFA6B91A)
val TypeRock = Color(0xFFB6A136)
val TypeGhost = Color(0xFF735797)
val TypeDragon = Color(0xFF6F35FC)
val TypeDark = Color(0xFF705746)
val TypeSteel = Color(0xFFB7B7CE)
val TypeFairy = Color(0xFFD685AD)
val TypeStellar = Color(0xFF4D8BA3)
val TypeUnknown = Color(0xFF4C675E)

val Colors.buttonBackgroundColor: Color
    @Composable
    get() = if (isLight) LightGray else DarkGray

val Colors.buttonIconColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.favouriteButtonColor: Color
    @Composable
    get() = if (isLight) LightMediumGray else MediumDarkGray