package com.example.kinopoiskapp.screens.more

import androidx.lifecycle.SavedStateHandle
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

class MoreScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
    movieUiMapper: MovieUiMapper,
) : ViewModel() {

    private val id = requireNotNull(savedStateHandle.get<String>(PARAM_MOVIE_ID))
    private val movieStatusListener = MovieMoreStatusListenerImpl()

    val moreMovieInfoStateFlow: StateFlow<MoreMovieUiModel> =
        moviesRepository.getMoreMoviesInfoStateFlow(id, movieStatusListener).map { movieItem ->
            movieUiMapper.mapMovieItemToMoreMovieUiModel(movieItem)

        }.stateIn(viewModelScope, SharingStarted.Lazily, MoreMovieUiModel())

    private val needShowErrorScreenMutableStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val needShowErrorScreenStateFlow: StateFlow<Boolean> =
        needShowErrorScreenMutableStateFlow


    private inner class MovieMoreStatusListenerImpl : MovieStatusListener {

        override fun onProgress(progress: Int) {
        }

        override fun onSuccess() {
            needShowErrorScreenMutableStateFlow.value = false
        }

        override fun onError() {
            needShowErrorScreenMutableStateFlow.value = true
        }
    }

    companion object {
        const val PARAM_MOVIE_ID = "movie_id"
    }
}