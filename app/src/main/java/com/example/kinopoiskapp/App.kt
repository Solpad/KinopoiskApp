package com.example.kinopoiskapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.kinopoiskapp.di.app.AppComponent
import com.example.kinopoiskapp.di.app.AppModule
import com.example.kinopoiskapp.di.app.DaggerAppComponent
import com.example.kinopoiskapp.di.main.DaggerMainComponent
import com.example.kinopoiskapp.di.main.MainComponent
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import javax.inject.Inject

class App : Application() {

    lateinit var mainComponent: MainComponent
        private set

    override fun onCreate() {
        super.onCreate()
        Log.e("onCreate","APPLICATION")

        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .build()

//        appComponent = DaggerAppComponent.create()
//        appComponent.inject(this)

    }
}

