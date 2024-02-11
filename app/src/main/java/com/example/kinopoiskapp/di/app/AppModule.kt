package com.example.kinopoiskapp.di.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.kinopoiskapp.di.DatabaseModule
import dagger.Module
import dagger.Provides

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
