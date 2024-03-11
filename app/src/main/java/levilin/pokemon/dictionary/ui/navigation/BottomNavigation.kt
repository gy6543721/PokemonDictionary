package levilin.pokemon.dictionary.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import levilin.pokemon.dictionary.ui.theme.buttonBackgroundColor
import levilin.pokemon.dictionary.ui.theme.buttonIconColor

@Composable
fun BottomNavView(navController: NavController, bottomBarState: MutableState<Boolean>) {
    val items = listOf(
        BottomNavItem.ChatRoomScreen,
        BottomNavItem.PokemonListScreen,
        BottomNavItem.FavoriteListScreen
    )

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                contentColor = MaterialTheme.colors.buttonIconColor
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(id = item.titleStringID)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.titleStringID),
                                fontSize = 10.sp
                            )
                        },
                        selectedContentColor = MaterialTheme.colors.buttonIconColor,
                        unselectedContentColor = MaterialTheme.colors.buttonIconColor.copy(0.4f),
                        alwaysShowLabel = true,
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}