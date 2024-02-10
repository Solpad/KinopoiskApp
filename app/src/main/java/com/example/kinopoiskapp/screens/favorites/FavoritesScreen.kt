package com.example.kinopoiskapp.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kinopoiskapp.screens.baseui.FilmsTopAppBar
import com.example.kinopoiskapp.screens.baseui.MoviesBottomAppBar

@Composable
internal fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel,
    navController: NavHostController,
    ) {
    val popularsFilms = viewModel.favoritesFilmsStateFlow

    FavoritesScreenContent(
        favoritesFilms = popularsFilms,
        topBar = { FilmsTopAppBar("Избранное") },
        bottomBar = { MoviesBottomAppBar(navController = navController) },

    )
}
@Composable
private fun FavoritesScreenContent(
    favoritesFilms: MutableList<FavoritesMovieUiModel>,
    topBar:@Composable () ->Unit,
    bottomBar:@Composable () ->Unit,
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Unspecified,
    ) { scaffoldPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = scaffoldPaddings.calculateTopPadding(),
                    bottom = scaffoldPaddings.calculateBottomPadding(),
                    start = scaffoldPaddings.calculateStartPadding(LocalLayoutDirection.current),
                    end = scaffoldPaddings.calculateEndPadding(LocalLayoutDirection.current),
                )
        ) {
            LazyColumn(){
                itemsIndexed(
                    favoritesFilms,
                    key = { _, film -> "popular_film_item_${film.id}" },
                ){ _, film ->
                    FavoriteFilmCard(film)
                }
            }
        }
    }
}

@Composable
private fun FavoriteFilmCard(
    film: FavoritesMovieUiModel
){
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = film.name
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = film.genre
        )
    }
}

@Preview
@Composable
fun FavoriteFilmCardPreview() {
}