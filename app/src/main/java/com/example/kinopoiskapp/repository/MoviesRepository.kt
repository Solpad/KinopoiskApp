package com.example.kinopoiskapp.repository

import com.example.kinopoiskapp.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMoreMoviesInfoStateFlow(
        id: String,
        movieStatusListener: MovieStatusListener
    ): Flow<MovieItem>

    fun getPopularMoviesInfoStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>>

//    val favoritesMoviesStateFlow: Flow<List<MovieItem>>

    suspend fun getAllMovies(movieStatusListener: MovieStatusListener): List<MovieItem>?

    suspend fun getMovieById(id: String, movieStatusListener: MovieStatusListener): MovieItem?

}