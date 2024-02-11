package com.example.kinopoiskapp.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.kinopoiskapp.MainActivity
import com.example.kinopoiskapp.screens.baseui.FilmsTopAppBar
import com.example.kinopoiskapp.screens.baseui.MoviesBottomAppBar

@Composable
internal fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel,
    navController: NavHostController,
) {
    val favoritesFilms by viewModel.favoritesFilmsStateFlow.collectAsState()

    val onFavoriteMovieClick = remember {
        { movieId: String ->
            navController.navigate(
                "${MainActivity.SCREEN_MORE_INFO}/$movieId"
            )
        }
    }

    FavoritesScreenContent(
        favoritesFilms = favoritesFilms,
        onFavoriteMovieClick = onFavoriteMovieClick,
        topBar = { FilmsTopAppBar("Избранное") },
        bottomBar = { MoviesBottomAppBar(navController = navController) },
        onFavoriteMovieLongPressed = viewModel::onFavoriteMovieLongPressed
    )
}

@Composable
private fun FavoritesScreenContent(
    favoritesFilms: List<FavoritesMovieUiModel>,
    onFavoriteMovieClick: (String) -> Unit,
    onFavoriteMovieLongPressed: (String) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
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
            LazyColumn() {
                itemsIndexed(
                    favoritesFilms,
                    key = { _, film -> "popular_film_item_${film.id}" },
                ) { _, film ->
                    FavoriteFilmCard(film, onFavoriteMovieClick, onFavoriteMovieLongPressed)
                }
            }
        }
    }
}


@Composable
private fun FavoriteFilmCard(
    film: FavoritesMovieUiModel,
    onFavoriteMovieClick: (String) -> Unit,
    onFavoriteMovieLongPressed: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                role = Role.Button,
                onClick = { onFavoriteMovieClick(film.id.toString()) },
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onFavoriteMovieLongPressed(film.id.toString()) },
                )
            }

    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = film.posterSmall, contentDescription = "content",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(100.dp)
            )
            Column {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = film.name,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .alpha(0.5f),
                    text = film.genre,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
