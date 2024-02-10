package com.example.kinopoiskapp.screens.mapper

import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.screens.more.MoreMovieUiModel
import com.example.kinopoiskapp.screens.popular.PopularMovieUiModel

interface MovieUiMapper {

    fun mapMovieItemToPopularMovieUiModel(
        movies: List<MovieItem>
    ): List<PopularMovieUiModel>

    fun mapMovieItemToMoreMovieUiModel(
        movie: MovieItem
    ): MoreMovieUiModel
}