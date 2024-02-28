package levilin.pokemon.dictionary.ui.screen.list.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import levilin.pokemon.dictionary.ui.theme.buttonIconColor
import levilin.pokemon.dictionary.viewmodel.list.PokemonListViewModel

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    viewModel: PokemonListViewModel
) {
    // Focus Control
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Search Query
    val inputText by viewModel.inputText.collectAsState()
    var isHintDisplayed by remember { mutableStateOf(hint != "") }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                isHintDisplayed = true
                viewModel.clearInputText()
                focusManager.clearFocus()
            },
            modifier = Modifier.padding(end = 5.dp)
        ) {
            androidx.compose.material.Icon(
                modifier = Modifier.alpha(ContentAlpha.medium),
                imageVector = Icons.Filled.Close,
                tint = MaterialTheme.colors.buttonIconColor,
                contentDescription = "clear"
            )
        }
    }

    Box(modifier = modifier) {
        TextField(
            value = inputText,
            onValueChange = { inputValue ->
                viewModel.setInputText(inputValue = inputValue)
                if (inputValue.isNotBlank()) {
                    isHintDisplayed = false
                }
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                fontSize = 15.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = MaterialTheme.colors.background)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground
                    ), shape = RoundedCornerShape(50)
                )
                .align(Alignment.Center)
                .onFocusChanged { focusState ->
                    isHintDisplayed = !focusState.isFocused && inputText.isBlank()
                    if (!focusState.isFocused) {
                        keyboardController?.hide()
                    }
                },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onBackground,
                errorCursorColor = Color.Red
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.searchPokemonList(inputText)
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            trailingIcon = if (inputText.isNotEmpty()) trailingIconView else null
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}