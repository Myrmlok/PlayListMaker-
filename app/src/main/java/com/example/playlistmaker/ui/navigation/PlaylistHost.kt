package com.example.playlistmaker.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.presentation.search.SearchViewModel
import com.example.playlistmaker.ui.main.MainScreen
import com.example.playlistmaker.ui.search.DetailsScreen
import com.example.playlistmaker.ui.search.SearchScreen
import com.example.playlistmaker.ui.setting.SettingsScreen
import com.google.gson.Gson

private const val TRACK_JSON_ARG = "trackJson"

@Composable
fun PlaylistHost(
    navController: NavHostController,
    gson: Gson,
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {

    fun navigateTo(screen: PlaylistScreen) {
        navController.navigate(screen.name) {
            launchSingleTop = true
        }
    }

    fun navigateToSearch() = navigateTo(PlaylistScreen.Search)

    fun navigateToSettings() = navigateTo(PlaylistScreen.Settings)

    // JSON кодируем, чтобы символы вроде / и ? в названиях не ломали маршрут
    fun navigateToDetails(track: Track) {
        navController.navigate("${PlaylistScreen.Details.name}/${Uri.encode(gson.toJson(track))}")
    }

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
            SearchScreen(
                searchViewModel = searchViewModel,
                navigateToDetailScreen = { track -> navigateToDetails(track) },
                onBackClick = { navigateBack() },
            )
        }
        composable("${PlaylistScreen.Details.name}/{$TRACK_JSON_ARG}") { backStackEntry ->
            val trackJson = backStackEntry.arguments?.getString(TRACK_JSON_ARG)
            if (trackJson != null) {
                DetailsScreen(track = gson.fromJson(trackJson, Track::class.java))
            }
        }
        composable(PlaylistScreen.Settings.name) {
            SettingsScreen(onBackClick = { navigateBack() })
        }
    }
}
