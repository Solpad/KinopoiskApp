package com.example.kinopoiskapp.util
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject


class NetworkProvider (private val connectivityManager: ConnectivityManager){

    fun checkInternet (): Boolean {
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