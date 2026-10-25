package com.example.playlistmaker.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.theme.YpGray

// Заглушка экрана деталей трека, будет развиваться в следующих спринтах
@Composable
fun DetailsScreen(
    track: Track,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = track.trackName, fontSize = 22.sp, fontWeight = FontWeight.Medium)
        Text(text = track.artistName, modifier = Modifier.padding(top = 8.dp), fontSize = 16.sp)
        Text(text = track.trackTime, modifier = Modifier.padding(top = 8.dp), fontSize = 14.sp, color = YpGray)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DetailsScreenPreview() {
    DetailsScreen(Track(1, "Yesterday (Remastered 2009)", "The Beatles", "02:05"))
}
