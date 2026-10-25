package com.example.playlistmaker.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.network.TracksRepositoryImpl
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.presentation.search.SearchState
import com.example.playlistmaker.presentation.search.SearchViewModel
import com.example.playlistmaker.ui.theme.PlayListMakerTheme
import com.example.playlistmaker.ui.theme.YpGray
import com.example.playlistmaker.ui.theme.YpLightGray

private val searchHistory = listOf("Rammstein", "Queen", "Bruno Mars")

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navigateToDetailScreen: (Track) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    val searchState = rememberTextFieldState()
    var isFocused by remember { mutableStateOf(false) }
    val showHistory = isFocused && searchState.text.isEmpty()

    Column(modifier = modifier.fillMaxSize()) {
        SearchTopBar(onBackClick = onBackClick)
        SearchField(
            state = searchState,
            showHistory = showHistory,
            onFocusChange = { isFocused = it },
            onSearch = { query -> searchViewModel.searchTracks(query) },
            onClear = { searchViewModel.clearSearch() },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )
        SearchResults(
            screenState = screenState,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            onTrackClick = navigateToDetailScreen,
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
    onSearch: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    fun search() {
        if (state.text.isNotEmpty()) {
            onSearch(state.text.toString())
            focusManager.clearFocus()
        }
    }

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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            onKeyboardAction = { search() },
            decorator = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.search),
                        contentDescription = stringResource(R.string.search_icon),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { search() },
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_tracks_placeholder),
                                fontSize = 16.sp,
                                color = YpGray,
                            )
                        }
                        innerTextField()
                    }
                    if (state.text.isNotEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.clear),
                            contentDescription = stringResource(R.string.clear_search),
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    state.clearText()
                                    focusManager.clearFocus()
                                    onClear()
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
                    onClick = {
                        state.setTextAndPlaceCursorAtEnd(item)
                        search()
                    },
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

@Composable
private fun SearchResults(
    screenState: SearchState,
    modifier: Modifier,
    onTrackClick: (Track) -> Unit,
) {
    when (screenState) {
        is SearchState.Initial -> {
            // Ничего не показываем в начальном состоянии
        }

        is SearchState.Searching -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SearchState.Success -> {
            val tracks = screenState.foundList
            if (tracks.isEmpty()) {
                InformerState(stringResource(R.string.no_tracks_found))
            } else {
                LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(tracks.size) { index ->
                        TrackListItem(
                            track = tracks[index],
                            onClick = { onTrackClick(tracks[index]) }
                        )
                    }
                }
            }
        }

        is SearchState.Fail -> {
            InformerState(
                titleText = screenState.error,
                subtitleText = stringResource(R.string.check_connection)
            )
        }
    }
}

@Composable
private fun InformerState(
    titleText: String,
    subtitleText: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        subtitleText?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                fontSize = 14.sp,
                color = YpGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TrackListItem(
    track: Track,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Иконка трека (заглушка)
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = null,
            modifier = Modifier.size(45.dp),
            tint = YpGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                track.trackName,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = track.artistName,
                    modifier = Modifier.weight(1f, fill = false),
                    fontSize = 11.sp,
                    color = YpGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "•",
                    fontSize = 16.sp,
                    color = YpGray,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    track.trackTime,
                    fontSize = 11.sp,
                    color = YpGray,
                    maxLines = 1,
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.vector),
            contentDescription = stringResource(R.string.arrow_forward),
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    PlayListMakerTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(
                searchViewModel = remember { SearchViewModel(TracksRepositoryImpl(RetrofitNetworkClient())) },
                navigateToDetailScreen = { },
                onBackClick = { },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
