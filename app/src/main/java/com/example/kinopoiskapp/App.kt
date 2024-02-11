package com.example.kinopoiskapp

import android.app.Application
import com.example.kinopoiskapp.di.app.AppModule
import com.example.kinopoiskapp.di.main.DaggerMainComponent
import com.example.kinopoiskapp.di.main.MainComponent

class App : Application() {

    lateinit var mainComponent: MainComponent
        private set

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}

