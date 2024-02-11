package com.example.kinopoiskapp.repository.manager

import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.repository.MoviesChangesListener

interface MovieManager {

    fun notifyMovieChangesListeners(movies: List<MovieItem>)

    fun getFavoriteMovie(): List<MovieItem>

    fun addMoviesChangesListener(listener: MoviesChangesListener)

    fun removeMoviesChangesListener(listener: MoviesChangesListener)
}