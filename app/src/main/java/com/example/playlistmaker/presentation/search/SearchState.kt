package com.example.playlistmaker.presentation.search

import com.example.playlistmaker.domain.model.Track

sealed class SearchState {
    object Initial : SearchState() // Состояние экрана при первой загрузке
    object Searching : SearchState() // Состояние экрана при начале поиска
    data class Success(val foundList: List<Track>) : SearchState() // Состояние экрана при успешном завершении поиска
    data class Fail(val error: String) : SearchState() // Состояние экрана, если при запросе к серверу произошла ошибка
}
