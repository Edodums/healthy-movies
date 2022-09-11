package com.example.movies.ui.movie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.md_theme_dark_onPrimaryContainer
import com.example.compose.md_theme_dark_surfaceVariant
import com.example.movies.R
import com.example.movies.ui.models.Movie
import com.example.movies.ui.utils.NetworkImage


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieDetails(movieViewModel: MovieViewModel = viewModel()) {
    val movie = movieViewModel.movie.collectAsState().value
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Expanded
        )
    )

    BottomSheetScaffold(
        sheetContent = { SheetContent(movie) },
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = md_theme_dark_surfaceVariant.copy(alpha = 0.92f),
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(16.dp),
        sheetPeekHeight = 400.dp
    ) {
        BehindSheet(movie)
    }
}

@Composable
fun BehindSheet(movie: Movie) {
    NetworkImage(
        url = movie.posterUrl,
        contentDescription = movie.plot,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    )
}

@Composable
fun SheetContent(movie: Movie) {
    Text(
        text = movie.title,
        textAlign = TextAlign.Left,
        fontSize = 24.sp,
        modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 4.dp),
        color = md_theme_dark_onPrimaryContainer,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(R.string.overview),
        textAlign = TextAlign.Left,
        fontSize = 20.sp,
        modifier = Modifier.padding(12.dp, 4.dp, 12.dp, 2.dp),
        color = md_theme_dark_onPrimaryContainer,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = movie.plot,
        textAlign = TextAlign.Left,
        fontSize = 18.sp,
        modifier = Modifier.padding(12.dp, 4.dp, 12.dp, 4.dp)
    )
}
