package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.playlistmaker.nav.PlaylistHost
import com.example.playlistmaker.nav.PlaylistScreen
import com.example.playlistmaker.ui.theme.PlayListMakerTheme
import org.intellij.lang.annotations.JdkConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayListMakerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlaylistHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreen(
    onSearchClick: () -> Unit={},
    onPlaylistsClick: () -> Unit={},
    onFavoritesClick: () -> Unit={},
    onSettingsClick: () -> Unit={},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue)
            .statusBarsPadding(),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(top = 12.dp),
        ) {
            MainMenuItem(
                iconRes = R.drawable.search,
                title = stringResource(R.string.tittle_search),
                onClick = onSearchClick,
            )
            MainMenuItem(
                iconRes = R.drawable.playlist,
                title = stringResource(R.string.tittle_playlists),
                onClick = onPlaylistsClick,
            )
            MainMenuItem(
                iconRes = R.drawable.favorites,
                title = stringResource(R.string.tittle_favourites),
                onClick = onFavoritesClick,
            )
            MainMenuItem(
                iconRes = R.drawable.settings,
                title = stringResource(R.string.tittle_settings),
                onClick = onSettingsClick,
            )
        }
    }
}

@Composable
private fun MainMenuItem(
    @DrawableRes iconRes: Int,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
        )
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
        )
        Image(
            painter = painterResource(R.drawable.vector),
            contentDescription = null,
        )
    }
}