package levilin.pokemon.dictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import levilin.pokemon.dictionary.ui.theme.PokemonDictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import levilin.pokemon.dictionary.ui.navigation.BottomNavView
import levilin.pokemon.dictionary.ui.navigation.NavGraphView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonDictionaryTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

                Scaffold(bottomBar = { BottomNavView(navController = navController, bottomBarState = bottomBarState) }) { innerPadding ->
                    var paddingValue = innerPadding
                    when (navBackStackEntry?.destination?.route) {
                        "chat_room_screen" -> {
                            bottomBarState.value = true
                        }
                        "pokemon_list_screen" -> {
                            bottomBarState.value = true
                        }
                        "favorite_list_screen" -> {
                            bottomBarState.value = true
                        }
                        else -> {
                            bottomBarState.value = false
                            paddingValue = PaddingValues(0.dp)
                        }
                    }
                    NavGraphView(
                        modifier = Modifier.padding(paddingValues = paddingValue),
                        navController = navController
                    )
                }
            }
        }
    }
}
