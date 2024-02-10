package com.example.kinopoiskapp

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kinopoiskapp.MainActivity.Companion.SCREEN_FAVORITES
import com.example.kinopoiskapp.MainActivity.Companion.SCREEN_POPULAR
import com.example.kinopoiskapp.screens.baseui.Screen
import com.example.kinopoiskapp.screens.favorites.FavoritesScreen
import com.example.kinopoiskapp.screens.more.MoreScreen
import com.example.kinopoiskapp.screens.more.MoreScreenViewModel.Companion.PARAM_MOVIE_ID
import com.example.kinopoiskapp.screens.popular.PopularScreen

@Composable
fun FilmsNavigationRoot(
    viewModelFactory: ViewModelProvider.Factory,
) {
    val navHostController = rememberNavController()

    Box() {
        NavHost(
            navController = navHostController,
            startDestination = SCREEN_POPULAR,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width -> width },
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width -> -width },
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width -> -width },
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width -> width },
                )
            }
        ) {
            composable(SCREEN_POPULAR) {
                PopularScreen(
                    viewModel = viewModel(factory = viewModelFactory),
                    navController = navHostController
                )
            }
            composable(SCREEN_FAVORITES) {
                FavoritesScreen(
                    viewModel = viewModel(factory = viewModelFactory),
                    navController = navHostController
                )
            }
            composable(
                route =
                MainActivity.SCREEN_MORE_INFO +
                        "/{" +
                        PARAM_MOVIE_ID +
                        "}",
                arguments =
                listOf(
                    navArgument(PARAM_MOVIE_ID) {
                        type = NavType.StringType
                    }
                )
            ) {
                MoreScreen(
                    viewModel = viewModel(factory = viewModelFactory),
                )
            }
        }
    }
}
