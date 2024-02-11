package com.example.kinopoiskapp.repository

interface MovieStatusListener {

    fun onSuccess()
    fun onError()
    fun onRepeat(): Boolean

}