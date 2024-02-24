package levilin.pokemon.dictionary.utility

import androidx.compose.ui.graphics.Color
import levilin.pokemon.dictionary.data.remote.response.Type
import levilin.pokemon.dictionary.data.remote.response.Stat
import levilin.pokemon.dictionary.ui.theme.AtkColor
import levilin.pokemon.dictionary.ui.theme.DefColor
import levilin.pokemon.dictionary.ui.theme.HPColor
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
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}