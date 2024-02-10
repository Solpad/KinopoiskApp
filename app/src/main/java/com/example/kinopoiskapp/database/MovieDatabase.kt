package com.example.kinopoiskapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinopoiskapp.model.MovieItem

@Database(
    entities = [MovieItem::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun getDao(): MovieDao

    companion object {
        @Volatile
        var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context): MovieDatabase {
            Log.e("db context","${context.applicationContext}")
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_db.db"
            ).build()
        }
    }
}