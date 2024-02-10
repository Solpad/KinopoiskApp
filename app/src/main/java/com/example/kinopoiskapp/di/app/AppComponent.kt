package com.example.kinopoiskapp.di.app

import android.app.Application
import com.example.kinopoiskapp.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {
    fun inject(application: Application)
}