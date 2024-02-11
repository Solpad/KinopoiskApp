package com.example.kinopoiskapp.repository.manager

import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.repository.MoviesChangesListener

interface MovieManager {

    fun setFavoriteMovie(movies: List<MovieItem>)
    fun setPopularMovie(movies: List<MovieItem>)
    fun setMovieById(movie: MovieItem)
    fun notifyMovieChangesListeners()
    fun getFavoriteMovie(): List<MovieItem>
    fun getPopularMovie(): List<MovieItem>
    fun getMovieById(): MovieItem?
    fun addMoviesChangesListener(listener: MoviesChangesListener)
    fun removeMoviesChangesListener(listener: MoviesChangesListener)
}