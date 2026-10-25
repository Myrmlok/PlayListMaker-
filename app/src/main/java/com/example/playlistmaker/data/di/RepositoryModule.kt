package com.example.playlistmaker.data.di

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.network.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<NetworkClient> {
        RetrofitNetworkClient()
    }
    factory<TracksRepository> {
        TracksRepositoryImpl(get())
    }
}
