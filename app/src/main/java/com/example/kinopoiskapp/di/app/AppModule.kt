package com.example.kinopoiskapp.di.app

import com.example.kinopoiskapp.util.NetworkProvider
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.kinopoiskapp.di.NetworkModule
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [NetworkModule::class,AppBindModule::class],
)
class AppModule {

    @Provides
    @AppScope
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }
    @Provides
    @AppScope
    fun provideConnectivityManager(appContext: Context): ConnectivityManager {
        return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

@Module
interface AppBindModule{
    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl):MoviesRepository
}