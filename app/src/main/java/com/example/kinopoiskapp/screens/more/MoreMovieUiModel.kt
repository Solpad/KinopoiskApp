package com.example.kinopoiskapp.screens.more

import com.example.kinopoiskapp.model.network.Country

data class MoreMovieUiModel(
    val id:Int? = null,
    val poster: String? = null,
    val name:String? = null,
    val description:String? = null,
    val genre:String? = null,
    val country: String? = null,
)