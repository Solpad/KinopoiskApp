package com.example.kinopoiskapp.di.app

import com.example.kinopoiskapp.util.NetworkProvider
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.kinopoiskapp.di.DatabaseModule
import com.example.kinopoiskapp.di.NetworkModule
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [DatabaseModule::class],
)
class AppModule(val app: Application) {

    @Provides
    @AppScope
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Provides
    @AppScope
    fun provideConnectivityManager(appContext: Context): ConnectivityManager {
        return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
