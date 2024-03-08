package levilin.pokemon.dictionary.utility

import androidx.compose.ui.graphics.Color
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.model.remote.pokemon.Stat
import levilin.pokemon.dictionary.model.remote.pokemon.Type
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

fun parseStatusToLocalizedStringId(status: Stat): Int {
    return when(status.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> R.string.text_hp
        "attack" -> R.string.text_atk
        "defense" -> R.string.text_def
        "special-attack" -> R.string.text_sp_atk
        "special-defense" -> R.string.text_sp_def
        "speed" -> R.string.text_spd
        else -> R.string.type_unknown
    }
}

fun toLocalizedFirstIntro(): String {
    return when(Locale.getDefault().language) {
        "ja" -> "はじめまして！\nポケット モンスターの せかいへ ようこそ！"
        "zh" -> "你好，我是大木博士！"
        else -> "Hello there!\nWelcome to the world of Pokémon!"
    }
}

fun toLocalizedSecondIntro(): String {
    return when(Locale.getDefault().language) {
        "ja" -> "わたしの なまえは オーキド！\nみんなからは ポケモン はかせと したわれて おるよ！"
        "zh" -> "歡迎來到神奇寶貝的世界。很高興見到你！"
        else -> "My name is Oak!\nPeople call me the Pokémon Prof!"
    }
}

fun toLocalizedThirdIntro(): String {
    return when(Locale.getDefault().language) {
        "ja" -> "この せかいには ポケット モンスターと よばれる！"
        "zh" -> "你對神奇寶貝世界有什麼問題嗎？我希望能盡我所能回答你的問題。"
        else -> "This world is inhabited by creatures called Pokémon!"
    }
}