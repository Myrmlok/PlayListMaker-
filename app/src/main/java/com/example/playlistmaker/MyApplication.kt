package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.data.di.repositoryModule
import com.example.playlistmaker.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            val viewModelModule = module {
                viewModel {
                    SearchViewModel(get())
                }
            }
            modules(repositoryModule, viewModelModule)
        }
    }
}
