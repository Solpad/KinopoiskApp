package com.example.kinopoiskapp.screens.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoiskapp.repository.MovieStatusListener
import com.example.kinopoiskapp.repository.MoviesRepository
import com.example.kinopoiskapp.screens.mapper.MovieUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PopularScreenViewModel(
    private val moviesRepository: MoviesRepository,
    private val movieUiMapper: MovieUiMapper,
) : ViewModel() {

    private val movieStatusListener = MoviePopularStatusListenerImpl()

    private val needShowErrorScreenMutableStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val needShowErrorScreenStateFlow: StateFlow<Boolean> =
        needShowErrorScreenMutableStateFlow

    private val needRepeatDownloadMutableStateFlow: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val needRepeatDownloadStateFlow: StateFlow<Boolean> =
        needRepeatDownloadMutableStateFlow


    var popularsMoviesStateFlow: StateFlow<List<PopularMovieUiModel>> =
        moviesRepository.getPopularMoviesStateFlow(movieStatusListener)
            .map { movies ->
                movieUiMapper
                    .mapMovieItemToPopularMovieUiModel(movies)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, mutableListOf())

    fun onAddToFavoriteClick(id: String) = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.addMovieToFavorites(id, movieStatusListener)
    }
    fun onRepeatErrorButton() = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.repeatDownloadMovie(movieStatusListener)
    }

    private inner class MoviePopularStatusListenerImpl : MovieStatusListener {
        override fun onProgress(progress: Int) {
        }

        override fun onSuccess() {
            needShowErrorScreenMutableStateFlow.value = false
        }

        override fun onError() {
            needShowErrorScreenMutableStateFlow.value = true
        }

        override fun onRepeat():Boolean {
            Log.e("onRepeatErrorButton","true")
            return needRepeatDownloadStateFlow.value
        }
    }
}