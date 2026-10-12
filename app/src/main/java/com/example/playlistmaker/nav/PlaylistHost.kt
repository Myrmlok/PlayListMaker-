package com.example.playlistmaker.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmaker.MainScreen
import com.example.playlistmaker.SearchScreen
import com.example.playlistmaker.SettingsScreen

@Composable
fun PlaylistHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    fun navigateTo(screen: PlaylistScreen) {
        navController.navigate(screen.name) {
            launchSingleTop = true
        }
    }

    fun navigateToSearch() = navigateTo(PlaylistScreen.Search)

    fun navigateToSettings() = navigateTo(PlaylistScreen.Settings)

    fun navigateBack() {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }


    NavHost(
        navController = navController,
        startDestination = PlaylistScreen.Main.name,
        modifier = modifier,
    ) {
        composable(PlaylistScreen.Main.name) {
            MainScreen(
                onSearchClick = { navigateToSearch() },
                onPlaylistsClick = { },
                onFavoritesClick = {  },
                onSettingsClick = { navigateToSettings() },
            )
        }
        composable(PlaylistScreen.Search.name) {
            SearchScreen(onBackClick = { navigateBack() })
        }
        composable(PlaylistScreen.Settings.name) {
            SettingsScreen(onBackClick = { navigateBack() })
        }
    }
}