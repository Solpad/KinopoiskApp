package com.example.kinopoiskapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity
@Parcelize
data class MovieItem(
    @PrimaryKey
    val id: String,
    val name: String,
    val cover: String,
    val coverSmall: String,
    val year: String,
    val description: String,
    val countries: String,
    val genres: String,
) : Parcelable