package com.example.kinopoiskapp.di.main

import com.example.kinopoiskapp.MainActivity
import com.example.kinopoiskapp.di.app.AppModule
import com.example.kinopoiskapp.di.app.AppScope
import com.example.kinopoiskapp.di.app.MainModule
import dagger.Component

@Component(modules = [MainModule::class])
@AppScope
interface MainComponent {
    fun inject(activity: MainActivity)
}