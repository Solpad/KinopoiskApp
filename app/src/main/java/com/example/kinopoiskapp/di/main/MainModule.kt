package com.example.kinopoiskapp.di.app

import com.example.kinopoiskapp.util.NetworkProvider
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.kinopoiskapp.di.DatabaseModule
import com.example.kinopoiskapp.di.NetworkModule
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [AppModule::class,NetworkModule::class,AppBindModule::class],
)
class MainModule {
}
@Module
interface AppBindModule{
    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl):MoviesRepository
}