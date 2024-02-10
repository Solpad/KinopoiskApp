package com.example.kinopoiskapp.screens.favorites

import androidx.lifecycle.ViewModel

class FavoritesScreenViewModel : ViewModel() {

    val favoritesFilmsStateFlow: MutableList<FavoritesMovieUiModel> =
        mutableListOf(
            FavoritesMovieUiModel(1, "Изгой-один", "Фантастика(2016)"),
            FavoritesMovieUiModel(2, "Щелкунчик", "Фэнтези(2018)"),
            FavoritesMovieUiModel(3, "Черный Адам", "Боевик(2022)"),
            FavoritesMovieUiModel(4, "Елки 9", "Комедия(202)"),
        )
}