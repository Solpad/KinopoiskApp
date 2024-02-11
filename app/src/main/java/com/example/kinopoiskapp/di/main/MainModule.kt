package com.example.kinopoiskapp.di.app

import com.example.kinopoiskapp.di.NetworkModule
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import com.example.kinopoiskapp.repository.manager.MovieManager
import com.example.kinopoiskapp.repository.manager.MovieManagerImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [AppModule::class, NetworkModule::class, AppBindModule::class],
)
class MainModule {

}

@Module
interface AppBindModule {
    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMoviesManager(movieManager: MovieManagerImpl): MovieManager
}