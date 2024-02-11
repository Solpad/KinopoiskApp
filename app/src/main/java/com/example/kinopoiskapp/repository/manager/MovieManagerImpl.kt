package com.example.kinopoiskapp.repository.manager

import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.repository.MoviesChangesListener
import javax.inject.Inject

class MovieManagerImpl @Inject constructor() : MovieManager {

    private var movieChangesListeners = mutableListOf<MoviesChangesListener>()

    private var favoriteMovies = listOf<MovieItem>()

    override fun getFavoriteMovie(): List<MovieItem> {
        return favoriteMovies
    }

    override fun addMoviesChangesListener(listener: MoviesChangesListener) {
        movieChangesListeners.add(listener)
    }

    override fun removeMoviesChangesListener(listener: MoviesChangesListener) {
        movieChangesListeners.remove(listener)
    }

    override fun notifyMovieChangesListeners(movies: List<MovieItem>) {
        favoriteMovies = movies
        for (listener in movieChangesListeners) {
            listener.onMoviesChanged()
        }
    }
}