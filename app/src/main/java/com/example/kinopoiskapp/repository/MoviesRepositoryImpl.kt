package com.example.kinopoiskapp.repository

import com.example.kinopoiskapp.database.MovieDao
import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.network.MovieApi
import com.example.kinopoiskapp.repository.manager.MovieManagerImpl
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MoviesRepositoryImpl
@Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val movieManager: MovieManagerImpl,
) : MoviesRepository {

    override fun getMoreMoviesInfoStateFlow(
        id: String,
        movieStatusListener: MovieStatusListener
    ): Flow<MovieItem> {
        return callbackFlow {
            val movieItem = getMovieById(id, movieStatusListener)
            if (movieItem != null) {
                send(movieItem)
            }
            awaitClose {}
        }
    }

    override fun getPopularMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            val movies = getAllMovies(movieStatusListener)
            trySendBlocking(movies)
            awaitClose { }
        }
    }

    override fun getFavoritesMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            val movies = getAllFavoritesMovies(movieStatusListener)
            val moviesChangesListener =
                object : MoviesChangesListener {
                    override fun onMoviesChanged() {
                        trySendBlocking(movieManager.getFavoriteMovie())
                    }
                }
            trySendBlocking(movies)
            movieManager.addMoviesChangesListener(moviesChangesListener)
            awaitClose { movieManager.removeMoviesChangesListener(moviesChangesListener) }
        }
    }

    override suspend fun getAllMovies(movieStatusListener: MovieStatusListener): List<MovieItem> {
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
            return mutableListOf()
        }
//        }else{350000
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
        try {
            movieItem?.let { movieDao.addMovie(movieItem) }
        } catch (_: Throwable) {
        }
        movieManager.notifyMovieChangesListeners(movieDao.getAll())
    }

    override suspend fun deleteMovieToFavorites(
        id: String,
        movieStatusListener: MovieStatusListener
    ) {
        val movieItem = getMovieById(id, movieStatusListener)
        try {
            movieItem?.let { movieDao.deleteMovie(it) }
        } catch (_: Throwable) {
        }
        movieManager.notifyMovieChangesListeners(movieDao.getAll())
    }
}