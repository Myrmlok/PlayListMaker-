package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.data.dto.TracksSearchRequest
import com.example.playlistmaker.data.dto.TracksSearchResponse
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.model.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    val tracksDto = listOf<TrackDto>(
        TrackDto(1, "Yesterday (Remastered 2009)", "The Beatles", 125000L),
        TrackDto(2, "Here Comes The Sun (Remastered...)", "The Beatles", 187000L),
        TrackDto(3, "No Reply", "The Beatles", 152000L),
        TrackDto(4, "Let It Be", "The Beatles", 243000L),
        TrackDto(5, "Girl", "The Beatles", 138000L),
        TrackDto(6, "Michelle", "The Beatles", 167000L),
        TrackDto(7, "Eleanor Rigby", "The Beatles", 198000L),
        TrackDto(8, "Come Together", "The Beatles", 259000L)
    )

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) { // успешный запрос
            return (response as TracksSearchResponse).results.map { it.toTrack() }
        } else {
            return emptyList()
        }
    }

    override suspend fun searchTracksContains(expression: String): List<Track> {
        return tracksDto
            .filter { track ->
                track.trackName.contains(expression, ignoreCase = true) ||
                        track.artistName.contains(expression, ignoreCase = true)
            }
            .map { it.toTrack() }
    }

    private fun TrackDto.toTrack(): Track {
        val seconds = trackTimeMillis / 1000
        val minutes = seconds / 60
        val trackTime = "%02d:%02d".format(minutes, seconds % 60)
        return Track(id, trackName, artistName, trackTime)
    }
}
