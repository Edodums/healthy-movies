package com.example.movies.ui.movies

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.compose.md_theme_dark_onPrimaryContainer
import com.example.compose.md_theme_dark_surface
import com.example.movies.ui.models.Movie
import com.example.movies.ui.utils.NetworkImage
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Movies(moviesViewModel: MoviesViewModel = viewModel(), onSelectedMovie: (Long) -> Unit) {
    val pagerState = rememberPagerState(1)
    val movies: LazyPagingItems<Movie> = moviesViewModel.getMovies().collectAsLazyPagingItems()
    Surface(color = md_theme_dark_onPrimaryContainer, modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = movies.itemCount,
            state = pagerState
        ) { page ->
            LazyRow {
                items(movies) { movie ->
                    movie?.let {
                        Card(
                            modifier = modifier(onSelectedMovie, movie, page),
                            elevation = 2.dp,
                            border = borderStroke()
                        ) {
                            Column(
                                modifier = Modifier
                                    .animateContentSize()
                                    .fillMaxWidth()
                                    .fillMaxHeight(2 / 3f)
                            ) {
                                NetworkImage(
                                    url = it.posterUrl,
                                    contentDescription = it.plot,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                )
                                Text(
                                    text = it.title,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillParentMaxWidth(2 / 3f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun borderStroke() = BorderStroke(
    1.dp, Brush.horizontalGradient(
        colors = listOf(
            md_theme_dark_onPrimaryContainer, md_theme_dark_surface
        )
    )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PagerScope.modifier(
    onSelectedMovie: (Long) -> Unit,
    movie: Movie,
    page: Int
) = Modifier
    .clickable {
        onSelectedMovie(movie.id)
    }
    .padding(18.dp)
    .graphicsLayer {
        // Calculate the absolute offset for the current page from the
        // scroll position. We use the absolute value which allows us to mirror
        // any effects for both directions
        val pageOffset =
            calculateCurrentOffsetForPage(page).absoluteValue

        // We animate the scaleX + scaleY, between 85% and 100%
        lerp(
            start = 0.35f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        // We animate the alpha, between 50% and 100%
        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }