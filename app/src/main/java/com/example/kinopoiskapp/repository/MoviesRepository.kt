package com.example.kinopoiskapp.repository

import com.example.kinopoiskapp.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMoreMoviesInfoStateFlow(
        id: String,
        movieStatusListener: MovieStatusListener
    ): Flow<MovieItem>

    fun getPopularMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>>
    fun getFavoritesMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>>
    suspend fun getAllMovies(movieStatusListener: MovieStatusListener): List<MovieItem>?
    suspend fun getAllFavoritesMovies(movieStatusListener: MovieStatusListener): List<MovieItem>?
    suspend fun getMovieById(id: String, movieStatusListener: MovieStatusListener): MovieItem?
    suspend fun addMovieToFavorites(id: String, movieStatusListener: MovieStatusListener)
    suspend fun deleteMovieToFavorites(id: String, movieStatusListener: MovieStatusListener)

}