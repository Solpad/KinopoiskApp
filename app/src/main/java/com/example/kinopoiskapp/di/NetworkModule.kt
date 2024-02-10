package com.example.kinopoiskapp.di


import com.example.kinopoiskapp.di.app.AppScope
import com.example.kinopoiskapp.network.MovieApi
import com.example.kinopoiskapp.util.BASEURL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    @AppScope
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun provideTodoApiRequest(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}