package com.example.kinopoiskapp.repository

import android.content.Context
import android.util.Log
import com.example.kinopoiskapp.database.MovieDao
import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.network.MovieApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
) : MoviesRepository {


    override fun getMoreMoviesInfoStateFlow(
        id: String,
        movieStatusListener: MovieStatusListener
    ): Flow<MovieItem> {
        return callbackFlow {
            getMovieById(id, movieStatusListener)?.let { send(it) }
            awaitClose { }
        }
    }

    override fun getPopularMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            getAllMovies(movieStatusListener)?.let { send(it) }
            awaitClose { }
        }
    }

    override fun getFavoritesMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            //Сделать слушателя изменений
            getAllFavoritesMovies(movieStatusListener).let { send(it) }
            awaitClose { }
        }
    }

    override suspend fun getAllMovies(movieStatusListener: MovieStatusListener): List<MovieItem>? {
//        if(networkProvider.checkInternet()) {
        val response = movieApi.getAllMovies()
        if (response.isSuccessful) {
            val body = response.body()
            val movieList = body?.films?.map { movieResponse ->
                MovieItem(
                    id = movieResponse.filmId.toString(),
                    name = movieResponse.nameRu ?: "",
                    cover = movieResponse.posterUrl ?: "",
                    coverSmall = movieResponse.posterUrlPreview ?: "",
                    year = movieResponse.year ?: "",
                    description = movieResponse.nameEn ?: "",
                    countries = movieResponse.countries.first().country ?: "",
                    genres = movieResponse.genres.first().genre ?: "",
                )
            }
            return movieList ?: mutableListOf()
        } else {
            movieStatusListener.onError()
            return null
        }
//        }else{
//            movieStatusListener.onError()
//            return null
//        }
    }

    override suspend fun getAllFavoritesMovies(movieStatusListener: MovieStatusListener): List<MovieItem> {
        return movieDao.getAll()
    }

    override suspend fun getMovieById(
        id: String,
        movieStatusListener: MovieStatusListener
    ): MovieItem? {
//        if (networkProvider.checkInternet()) {
        val response = movieApi.getMovieById(id)
        if (response.isSuccessful) {
            val body = response.body()
            movieStatusListener.onSuccess()
            return MovieItem(
                id = body?.kinopoiskId.toString(),
                name = body?.nameRu ?: "",
                cover = body?.posterUrl ?: "",
                coverSmall = body?.posterUrlPreview ?: "",
                year = body?.year.toString(),
                description = body?.description ?: "",
                countries = body?.countries?.first()?.country ?: "",
                genres = body?.genres?.first()?.genre ?: "",
            )
        } else {
            movieStatusListener.onError()
            return null
        }
//        } else {
//            movieStatusListener.onError()
//            return null
//        }
    }

    override suspend fun addMovieToFavorites(id: String, movieStatusListener: MovieStatusListener) {
        val movieItem = getMovieById(id, movieStatusListener)
        if (movieItem != null) {
            movieDao.addMovie(movieItem)
        }
    }
}