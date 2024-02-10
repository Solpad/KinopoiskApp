package com.example.kinopoiskapp.screens.baseui

import androidx.annotation.StringRes
import com.example.kinopoiskapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Favorites : Screen("favorites", R.string.favorite_title)
    data object Populars : Screen("populars", R.string.popular_title)
}