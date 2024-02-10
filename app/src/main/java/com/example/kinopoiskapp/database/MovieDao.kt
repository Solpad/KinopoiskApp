package com.example.kinopoiskapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoiskapp.model.MovieItem

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieitem")
    suspend fun getAll():List<MovieItem>

    @Insert
    suspend fun addMovie(movie: MovieItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllMovie(todoItems: List<MovieItem>)

    @Delete
    suspend fun deleteMovie(movie: MovieItem)

    @Query("DELETE FROM movieitem")
    suspend fun deleteAll()


}