package com.example.kinopoiskapp.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject


class NetworkProvider @Inject constructor(private val connectivityManager: ConnectivityManager) {
    fun checkInternet(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}