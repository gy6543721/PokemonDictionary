package levilin.pokemon.dictionary.ui.screen.detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import levilin.pokemon.dictionary.R
import levilin.pokemon.dictionary.utility.AdjustableText

@Composable
fun TypeLocalizedText(
    text: String,
    color: Color = Color.White,
    fontSize: TextUnit = 18.sp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    when(text) {
        "normal" -> AdjustableText(
            text = stringResource(id = R.string.type_normal),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "fire" -> AdjustableText(
            text = stringResource(id = R.string.type_fire),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "water" -> AdjustableText(
            text = stringResource(id = R.string.type_water),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "electric" -> AdjustableText(
            text = stringResource(id = R.string.type_electric),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "grass" -> AdjustableText(
            text = stringResource(id = R.string.type_grass),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "ice" -> AdjustableText(
            text = stringResource(id = R.string.type_ice),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "fighting" -> AdjustableText(
            text = stringResource(id = R.string.type_fighting),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "poison" -> AdjustableText(
            text = stringResource(id = R.string.type_poison),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "ground" -> AdjustableText(
            text = stringResource(id = R.string.type_ground),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "flying" -> AdjustableText(
            text = stringResource(id = R.string.type_flying),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "psychic" -> AdjustableText(
            text = stringResource(id = R.string.type_psychic),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "bug" -> AdjustableText(
            text = stringResource(id = R.string.type_bug),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "rock" -> AdjustableText(
            text = stringResource(id = R.string.type_rock),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "ghost" -> AdjustableText(
            text = stringResource(id = R.string.type_ghost),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "dragon" -> AdjustableText(
            text = stringResource(id = R.string.type_dragon),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "dark" -> AdjustableText(
            text = stringResource(id = R.string.type_dark),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "steel" -> AdjustableText(
            text = stringResource(id = R.string.type_steel),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "fairy" -> AdjustableText(
            text = stringResource(id = R.string.type_fairy),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "stellar" -> AdjustableText(
            text = stringResource(id = R.string.type_stellar),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        "???" -> AdjustableText(
            text = stringResource(id = R.string.type_unknown),
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
        else -> AdjustableText(
            text = text,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}