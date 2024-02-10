package com.example.kinopoiskapp.model.network

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("filmId"           ) var filmId           : Int?                 = null,
    @SerializedName("nameRu"           ) var nameRu           : String?              = null,
    @SerializedName("nameEn"           ) var nameEn           : String?              = null,
    @SerializedName("year"             ) var year             : String?              = null,
    @SerializedName("filmLength"       ) var filmLength       : String?              = null,
    @SerializedName("countries"        ) var countries        : ArrayList<Country> = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<Genre>    = arrayListOf(),
    @SerializedName("rating"           ) var rating           : String?              = null,
    @SerializedName("ratingVoteCount"  ) var ratingVoteCount  : Int?                 = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?              = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?              = null,
    @SerializedName("ratingChange"     ) var ratingChange     : String?              = null,
    @SerializedName("isRatingUp"       ) var isRatingUp       : String?              = null,
    @SerializedName("isAfisha"         ) var isAfisha         : Int?                 = null

)