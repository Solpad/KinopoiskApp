package com.example.kinopoiskapp.screens.mapper

import android.util.Log
import com.example.kinopoiskapp.R
import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.screens.more.MoreMovieUiModel
import com.example.kinopoiskapp.screens.popular.PopularMovieUiModel

class MovieUiMapperImpl:MovieUiMapper {

    override fun mapMovieItemToPopularMovieUiModel(movies: List<MovieItem>)
    : List<PopularMovieUiModel>
    {
    val mappedPopularList: List<PopularMovieUiModel> =
        movies.map { movieItem ->
                PopularMovieUiModel(
                    id = movieItem.id.toInt(),
                    posterSmall = movieItem.coverSmall,
                    name = movieItem.name,
                    genre =  movieItem.genres + " (" + movieItem.year + ")"
                )
            }
    return mappedPopularList
}

    override fun mapMovieItemToMoreMovieUiModel(movie: MovieItem): MoreMovieUiModel {
        return MoreMovieUiModel(
            id = movie.id.toInt(),
            poster = movie.cover,
            name = movie.name,
            description = movie.description,
            genre = movie.genres,
            country = movie.countries,
        )
    }

}