package com.example.kinopoiskapp.repository

interface MovieStatusListener {

    fun onProgress(progress: Int)
    fun onSuccess()
    fun onError()
    fun onRepeat(): Boolean

}