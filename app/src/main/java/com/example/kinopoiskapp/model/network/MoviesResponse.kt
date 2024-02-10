package com.example.kinopoiskapp.model.network

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("pagesCount") var pagesCount : Int? = null,
    @SerializedName("films") var films : ArrayList<MovieResponse> = arrayListOf(),
)