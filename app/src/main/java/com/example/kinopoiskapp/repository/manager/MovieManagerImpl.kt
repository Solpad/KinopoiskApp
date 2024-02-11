package com.example.kinopoiskapp.repository.manager

import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.repository.MoviesChangesListener
import javax.inject.Inject

class MovieManagerImpl @Inject constructor() : MovieManager {

    private var movieChangesListeners = mutableListOf<MoviesChangesListener>()

    private var favoriteMovies = listOf<MovieItem>()
    private var popularMovie = listOf<MovieItem>()
    private var movieById: MovieItem? = null

    override fun getFavoriteMovie(): List<MovieItem> {
        return favoriteMovies
    }

    override fun getPopularMovie(): List<MovieItem> {
        return popularMovie
    }

    override fun getMovieById(): MovieItem? {
        return movieById
    }

    override fun addMoviesChangesListener(listener: MoviesChangesListener) {
        movieChangesListeners.add(listener)
    }

    override fun removeMoviesChangesListener(listener: MoviesChangesListener) {
        movieChangesListeners.remove(listener)
    }

    override fun notifyMovieChangesListeners() {
        for (listener in movieChangesListeners) {
            listener.onMoviesChanged()
        }
    }

    override fun setFavoriteMovie(movies: List<MovieItem>) {
        favoriteMovies = movies
    }

    override fun setPopularMovie(movies: List<MovieItem>) {
        popularMovie = movies
    }

    override fun setMovieById(movie: MovieItem) {
        movieById = movie
    }
}