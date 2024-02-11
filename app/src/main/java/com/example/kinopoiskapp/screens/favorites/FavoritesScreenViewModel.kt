package com.example.kinopoiskapp.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.repository.MovieStatusListener
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.screens.mapper.MovieUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesScreenViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieUiMapper: MovieUiMapper,
) : ViewModel() {

    private val movieStatusListener = MovieFavoriteStatusListenerImpl()

    val favoritesFilmsStateFlow: StateFlow<List<FavoritesMovieUiModel>> =
        moviesRepository.getFavoritesMoviesStateFlow(movieStatusListener)
            .map { movies ->
                movieUiMapper
                    .mapMovieItemToFavoritesMovieUiModel(movies)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, mutableListOf())


    fun onFavoriteMovieLongPressed(id: String) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.deleteMovieToFavorites(id, movieStatusListener)
    }

    private inner class MovieFavoriteStatusListenerImpl : MovieStatusListener {

        override fun onSuccess() {
        }

        override fun onError() {
        }

        override fun onRepeat(): Boolean {
            return false
        }
    }

}