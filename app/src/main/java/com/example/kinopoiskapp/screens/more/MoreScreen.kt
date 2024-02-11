package com.example.kinopoiskapp.screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
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
    )
}

@Composable
private fun MoreScreenContent(
    needShowErrorScreen: Boolean,
    moreMovieInfo: MoreMovieUiModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Unspecified,
    ) { scaffoldPaddings ->
        if (needShowErrorScreen) {
            Text(
                text = "Нет интернета",
                modifier = Modifier.fillMaxWidth(1f),
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
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
                                fontSize = 24.sp
                            )
                        }
                    }
                    item {
                        moreMovieInfo.description?.let { text ->
                            Text(
                                text = text,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    item {
                        moreMovieInfo.genre?.let { text ->
                            Text(
                                text = "${stringResource(id = R.string.genres)} $text",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    item {
                        moreMovieInfo.country?.let { text ->
                            Text(
                                text = "${stringResource(id = R.string.countres)} $text",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}