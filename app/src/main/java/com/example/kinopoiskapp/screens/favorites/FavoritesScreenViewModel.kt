package com.example.kinopoiskapp.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.repository.MovieStatusListener
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.screens.mapper.MovieUiMapper
import com.example.kinopoiskapp.screens.popular.PopularMovieUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesScreenViewModel(
    moviesRepository: MoviesRepository,
    private val movieUiMapper: MovieUiMapper,
) : ViewModel() {

    private val movieStatusListener = MovieFavoriteStatusListenerImpl()
//    val favoritesFilmsStateFlow: StateFlow<List<FavoritesMovieUiModel>> =
//        moviesRepository.getFavoritesMoviesStateFlow(movieStatusListener)
//            .map { movies ->
//                movieUiMapper
//                    .mapMovieItemToPopularMovieUiModel(movies)
//            }
//            .stateIn(viewModelScope, SharingStarted.Lazily, mutableListOf())


    private inner class MovieFavoriteStatusListenerImpl : MovieStatusListener {
        override fun onProgress(progress: Int) {
        }
        override fun onSuccess() {
        }
        override fun onError() {
        }
    }

}