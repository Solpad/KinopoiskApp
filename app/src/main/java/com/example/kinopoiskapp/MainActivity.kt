package com.example.kinopoiskapp

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kinopoiskapp.network.MovieApi
import com.example.kinopoiskapp.repository.MoviesRepositoryImpl
import com.example.kinopoiskapp.screens.favorites.FavoritesScreenViewModel
import com.example.kinopoiskapp.screens.mapper.MovieUiMapperImpl
import com.example.kinopoiskapp.screens.more.MoreScreenViewModel
import com.example.kinopoiskapp.screens.popular.PopularScreenViewModel
import com.example.kinopoiskapp.ui.theme.KinopoiskAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var moviesRepository:MoviesRepositoryImpl

    private val viewModelFactory = viewModelFactory {
        initializer {
            PopularScreenViewModel(
                moviesRepository,
                MovieUiMapperImpl()
                )
        }
        initializer {
            FavoritesScreenViewModel()
        }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            MoreScreenViewModel(
                savedStateHandle,
                moviesRepository,
                MovieUiMapperImpl()
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            KinopoiskAppTheme {
                val window: Window = this.window
//                window.setFlags(
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//                )
                window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
                FilmsNavigationRoot(viewModelFactory = viewModelFactory)
                }
            }
        }

    internal companion object{
        internal const val SCREEN_POPULAR = "populars"
        internal const val SCREEN_FAVORITES = "favorites"
        internal const val SCREEN_MORE_INFO = "more_info"
    }
}
