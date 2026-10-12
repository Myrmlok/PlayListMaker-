package com.example.playlistmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.ui.theme.PlayListMakerTheme
import com.example.playlistmaker.ui.theme.YpGray
import com.example.playlistmaker.ui.theme.YpLightGray

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayListMakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchScreen(
                        onBackClick = { finish() },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

private val searchHistory = listOf("Rammstein", "Queen", "Bruno Mars")

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchState = rememberTextFieldState()
    var isFocused by remember { mutableStateOf(false) }
    val showHistory = isFocused && searchState.text.isEmpty()

    Column(modifier = modifier.fillMaxSize()) {
        SearchTopBar(onBackClick = onBackClick)
        SearchField(
            state = searchState,
            showHistory = showHistory,
            onFocusChange = { isFocused = it },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
    }
}

@Composable
private fun SearchTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.arrow),
            contentDescription = "Назад",
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onBackClick,
            ),
        )
        Text(
            text = "Поиск",
            modifier = Modifier.padding(start = 24.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun SearchField(
    state: TextFieldState,
    showHistory: Boolean,
    onFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(YpLightGray),
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .onFocusChanged { onFocusChange(it.isFocused) },
            textStyle = TextStyle(fontSize = 16.sp),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Поиск",
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { /* логика поиска будет позже */ },
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.text.isEmpty()) {
                            Text(text = "Поиск", fontSize = 16.sp, color = YpGray)
                        }
                        innerTextField()
                    }
                    if (state.text.isNotEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.clear),
                            contentDescription = "Очистить",
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    state.clearText()
                                    focusManager.clearFocus()
                                },
                        )
                    }
                }
            },
        )

        if (showHistory) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = YpGray,
            )
            Spacer(modifier = Modifier.height(4.dp))
            searchHistory.forEach { item ->
                HistoryItem(
                    text = item,
                    onClick = { state.setTextAndPlaceCursorAtEnd(item) },
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun HistoryItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.sheduler),
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = YpGray,
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 16.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    PlayListMakerTheme {Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        SearchScreen(
            onBackClick = { },
            modifier = Modifier.padding(innerPadding),
        )
    }
    }
}