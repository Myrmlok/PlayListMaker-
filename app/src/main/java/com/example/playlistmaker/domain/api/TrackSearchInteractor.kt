package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.model.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): List<Track>
}
