package com.example.kinopoiskapp.screens.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.repository.MovieStatusListener
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.screens.mapper.MovieUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PopularScreenViewModel(
    moviesRepository: MoviesRepository,
    private val movieUiMapper: MovieUiMapper,
    ):ViewModel() {

    private val movieStatusListener = MoviePopularStatusListenerImpl()

    private val needShowErrorScreenMutableStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val needShowErrorScreenStateFlow: StateFlow<Boolean> =
        needShowErrorScreenMutableStateFlow


    val popularsMoviesStateFlow: StateFlow<List<PopularMovieUiModel>> =
        moviesRepository.getPopularMoviesStateFlow(movieStatusListener)
            .map { movies ->
                movieUiMapper
                    .mapMovieItemToPopularMovieUiModel(movies)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, mutableListOf())

    private inner class MoviePopularStatusListenerImpl : MovieStatusListener {
        override fun onProgress(progress: Int) {
        }
        override fun onSuccess() {
            needShowErrorScreenMutableStateFlow.value = true
        }
        override fun onError() {
            needShowErrorScreenMutableStateFlow.value = true
        }
    }
}