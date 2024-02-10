package com.example.kinopoiskapp.network

import com.example.kinopoiskapp.model.network.MovieByIdResponse
import com.example.kinopoiskapp.model.network.MovieResponse
import com.example.kinopoiskapp.model.network.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("api/v2.2/films/{id}")
    suspend fun getMovieById(@Path("id") movieId: String): Response<MovieByIdResponse>

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getAllMovies(): Response<MoviesResponse>

    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("/api/v2.2/films/top")
    suspend fun searchMovies(@Query("keyword") query: String): Response<MoviesResponse>
}