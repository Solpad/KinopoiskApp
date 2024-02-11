package com.example.kinopoiskapp.repository

import com.example.kinopoiskapp.database.MovieDao
import com.example.kinopoiskapp.model.MovieItem
import com.example.kinopoiskapp.network.MovieApi
import com.example.kinopoiskapp.repository.manager.MovieManagerImpl
import com.example.kinopoiskapp.util.NetworkProvider
import com.example.kinopoiskapp.util.StringUtils
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
    private val networkProvider: NetworkProvider,
) : MoviesRepository {

    override fun getMoreMoviesInfoStateFlow(
        id: String,
        movieStatusListener: MovieStatusListener
    ): Flow<MovieItem> {
        return callbackFlow {

            getMovieById(id, movieStatusListener)
            val moviesChangesListener =
                object : MoviesChangesListener {
                    override fun onMoviesChanged() {
                        movieManager.getMovieById()?.let { trySend(it) }
                    }
                }
            movieManager.getMovieById()?.let { send(it) }
            movieManager.addMoviesChangesListener(moviesChangesListener)
            awaitClose { movieManager.removeMoviesChangesListener(moviesChangesListener) }
        }
    }

    override fun getPopularMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            getAllMovies(movieStatusListener)
            val moviesChangesListener =
                object : MoviesChangesListener {
                    override fun onMoviesChanged() {
                        trySendBlocking(movieManager.getPopularMovie())
                    }
                }
            trySendBlocking(movieManager.getPopularMovie())
            movieManager.addMoviesChangesListener(moviesChangesListener)
            awaitClose { movieManager.removeMoviesChangesListener(moviesChangesListener) }
        }
    }

    override fun getFavoritesMoviesStateFlow(movieStatusListener: MovieStatusListener): Flow<List<MovieItem>> {
        return callbackFlow {
            movieManager.setFavoriteMovie(movieDao.getAll())
            val moviesChangesListener =
                object : MoviesChangesListener {
                    override fun onMoviesChanged() {
                        trySendBlocking(movieManager.getFavoriteMovie())
                    }
                }
            trySendBlocking(movieManager.getFavoriteMovie())
            movieManager.addMoviesChangesListener(moviesChangesListener)
            awaitClose { movieManager.removeMoviesChangesListener(moviesChangesListener) }
        }
    }

    private suspend fun getAllMovies(movieStatusListener: MovieStatusListener) {
        if (networkProvider.checkInternet()) {
            val response = movieApi.getAllMovies()
            if (response.isSuccessful) {
                val body = response.body()
                val movieList = body?.films?.map { movieResponse ->
                    MovieItem(
                        id = movieResponse.filmId.toString(),
                        name = movieResponse.nameRu ?: StringUtils.Empty,
                        cover = movieResponse.posterUrl ?: StringUtils.Empty,
                        coverSmall = movieResponse.posterUrlPreview ?: StringUtils.Empty,
                        year = movieResponse.year ?: StringUtils.Empty,
                        description = movieResponse.nameEn ?: StringUtils.Empty,
                        countries = movieResponse.countries.first().country ?: StringUtils.Empty,
                        genres = movieResponse.genres.first().genre ?: StringUtils.Empty,
                    )
                }
                if (movieList != null) {
                    movieStatusListener.onSuccess()
                    movieManager.setPopularMovie(movieList)
                }
            } else {
                movieStatusListener.onError()
            }
        } else {
            movieStatusListener.onError()
        }
    }

    private suspend fun getMovieById(
        id: String,
        movieStatusListener: MovieStatusListener
    ) {
        if (networkProvider.checkInternet()) {
            val response = movieApi.getMovieById(id)
            if (response.isSuccessful) {
                val body = response.body()
                movieStatusListener.onSuccess()
                movieManager.setMovieById(
                    MovieItem(
                        id = body?.kinopoiskId.toString(),
                        name = body?.nameRu ?: StringUtils.Empty,
                        cover = body?.posterUrl ?: StringUtils.Empty,
                        coverSmall = body?.posterUrlPreview ?: StringUtils.Empty,
                        year = body?.year.toString(),
                        description = body?.description ?: StringUtils.Empty,
                        countries = body?.countries?.first()?.country ?: StringUtils.Empty,
                        genres = body?.genres?.first()?.genre ?: StringUtils.Empty,
                    )
                )

            } else {
                movieStatusListener.onError()
            }
        } else {
            movieStatusListener.onError()
        }
    }

    override suspend fun addMovieToFavorites(id: String, movieStatusListener: MovieStatusListener) {
        getMovieById(id, movieStatusListener)
        try {
            movieManager.getMovieById()?.let { movieDao.addMovie(it) }
        } catch (_: Throwable) {
        }
        movieManager.setFavoriteMovie(movieDao.getAll())
        movieManager.notifyMovieChangesListeners()
    }

    override suspend fun deleteMovieToFavorites(
        id: String,
        movieStatusListener: MovieStatusListener
    ) {
        getMovieById(id, movieStatusListener)
        try {
            movieManager.getMovieById()?.let { movieDao.deleteMovie(it) }
        } catch (_: Throwable) {
        }
        movieManager.setFavoriteMovie(movieDao.getAll())
        movieManager.notifyMovieChangesListeners()
    }

    override suspend fun repeatDownloadMovie(movieStatusListener: MovieStatusListener) {
        getAllMovies(movieStatusListener)
        movieManager.notifyMovieChangesListeners()
    }

    override suspend fun repeatDownloadMovieById(
        id: String,
        movieStatusListener: MovieStatusListener
    ) {
        getMovieById(id, movieStatusListener)
        movieManager.notifyMovieChangesListeners()
    }
}