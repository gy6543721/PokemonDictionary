package levilin.pokemon.dictionary.utility

import androidx.compose.ui.graphics.Color
import levilin.pokemon.dictionary.data.remote.response.pokemon.Type
import levilin.pokemon.dictionary.data.remote.response.pokemon.Stat
import levilin.pokemon.dictionary.ui.theme.AtkColor
import levilin.pokemon.dictionary.ui.theme.DefColor
import levilin.pokemon.dictionary.ui.theme.HpColor
import levilin.pokemon.dictionary.ui.theme.SpAtkColor
import levilin.pokemon.dictionary.ui.theme.SpDefColor
import levilin.pokemon.dictionary.ui.theme.SpdColor
import levilin.pokemon.dictionary.ui.theme.TypeBug
import levilin.pokemon.dictionary.ui.theme.TypeDark
import levilin.pokemon.dictionary.ui.theme.TypeDragon
import levilin.pokemon.dictionary.ui.theme.TypeElectric
import levilin.pokemon.dictionary.ui.theme.TypeFairy
import levilin.pokemon.dictionary.ui.theme.TypeFighting
import levilin.pokemon.dictionary.ui.theme.TypeFire
import levilin.pokemon.dictionary.ui.theme.TypeFlying
import levilin.pokemon.dictionary.ui.theme.TypeGhost
import levilin.pokemon.dictionary.ui.theme.TypeGrass
import levilin.pokemon.dictionary.ui.theme.TypeGround
import levilin.pokemon.dictionary.ui.theme.TypeIce
import levilin.pokemon.dictionary.ui.theme.TypeNormal
import levilin.pokemon.dictionary.ui.theme.TypePoison
import levilin.pokemon.dictionary.ui.theme.TypePsychic
import levilin.pokemon.dictionary.ui.theme.TypeRock
import levilin.pokemon.dictionary.ui.theme.TypeSteel
import levilin.pokemon.dictionary.ui.theme.TypeStellar
import levilin.pokemon.dictionary.ui.theme.TypeUnknown
import levilin.pokemon.dictionary.ui.theme.TypeWater
import java.util.*

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        "stellar" -> TypeStellar
        "???" -> TypeUnknown
        else -> Color.Black
    }
}

fun parseStatusToColor(status: Stat): Color {
    return when(status.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HpColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatusToAbbreviation(status: Stat): String {
    return when(status.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SP-ATK"
        "special-defense" -> "SP-DEF"
        "speed" -> "SPD"
        else -> ""
    }
}