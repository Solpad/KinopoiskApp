package com.example.kinopoiskapp.di

import android.content.Context
import androidx.room.Room
import com.example.kinopoiskapp.database.MovieDao
import com.example.kinopoiskapp.database.MovieDatabase
import com.example.kinopoiskapp.di.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    @AppScope
    fun provideDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_db.db"
        ).build()
    }

    @Provides
    @AppScope
    fun provideDatabaseDao(database: MovieDatabase): MovieDao {
        return database.getDao()
    }

}