package com.example.movies.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import com.example.movies.ui.movie.MovieDetails
import com.example.movies.ui.movie.MovieViewModel
import com.example.movies.ui.movies.Movies
import com.example.movies.ui.movies.MoviesViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


internal sealed class Screen(val route: String) {
    object Movies : Screen("Movies")
}

private sealed class MoviesScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Movies : MoviesScreen("movies")

    object MovieDetails : MoviesScreen("movie/{movieId}") {
        fun createRoute(root: Screen, movieId: Long): String {
            return "${root.route}/movie/$movieId"
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun Nav() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.Movies.route) {
        navigation(
            route = Screen.Movies.route,
            startDestination = MoviesScreen.Movies.createRoute(Screen.Movies)
        ) {
            addMovies(navController, Screen.Movies)
            addMovieDetail(Screen.Movies)
        }
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addMovies(
    navController: NavController,
    root: Screen
) {
    composable(
        route = MoviesScreen.Movies.createRoute(root)
    ) {
        val moviesViewModel = hiltViewModel<MoviesViewModel>()
        Movies(
            moviesViewModel = moviesViewModel,
            onSelectedMovie = { movieId ->
                navController.navigate(
                    MoviesScreen.MovieDetails.createRoute(
                        root,
                        movieId
                    ) + "?arg=From Movie Details Screen"
                )
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addMovieDetail(root: Screen) {
    composable(
        route = MoviesScreen.MovieDetails.createRoute(root),
        arguments = listOf(
            navArgument("movieId") { type = NavType.LongType }
        )
    ) {
        val movieViewModel = hiltViewModel<MovieViewModel>()
        MovieDetails(movieViewModel = movieViewModel)
    }
}