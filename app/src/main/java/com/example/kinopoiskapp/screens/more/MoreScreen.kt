package com.example.kinopoiskapp.screens.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kinopoiskapp.R


@Composable
internal fun MoreScreen(
    viewModel: MoreScreenViewModel,
) {
    val moreFilmInfo by viewModel.moreMovieInfoStateFlow.collectAsState()
    val needShowErrorScreen by viewModel.needShowErrorScreenStateFlow.collectAsState()

    MoreScreenContent(
        needShowErrorScreen = needShowErrorScreen,
        moreMovieInfo = moreFilmInfo,
        onRepeatErrorButton = viewModel::onRepeatErrorButton
    )
}

@Composable
private fun MoreScreenContent(
    needShowErrorScreen: Boolean,
    moreMovieInfo: MoreMovieUiModel,
    onRepeatErrorButton: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { scaffoldPaddings ->
        if (needShowErrorScreen) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kinopoisk_no_internet_icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(12.dp)
                    )
                    Text(
                        text = "Произошла ошибка при загрузке данных, проверьте подключение к сети",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF0094FF),
                        modifier = Modifier.padding(12.dp),
                    )
                    Button(
                        modifier = Modifier.padding(12.dp),
                        onClick = onRepeatErrorButton,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0094FF),
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Повторить")
                    }
                }
            }
        } else {
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
                LazyColumn(
                    modifier = Modifier
                ) {
                    item {
                        AsyncImage(
                            model = moreMovieInfo.poster, contentDescription = "poster",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    item {
                        moreMovieInfo.name?.let { text ->
                            Text(
                                text = text,
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    item {
                        moreMovieInfo.description?.let { text ->
                            Text(
                                text = text,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    item {
                        moreMovieInfo.genre?.let { text ->
                            Text(
                                text = "${stringResource(id = R.string.genres)} $text",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    item {
                        moreMovieInfo.country?.let { text ->
                            Text(
                                text = "${stringResource(id = R.string.countres)} $text",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}