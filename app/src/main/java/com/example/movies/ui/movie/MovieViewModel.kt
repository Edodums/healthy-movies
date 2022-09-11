package com.example.movies.ui.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repositories.MovieRepository
import com.example.movies.ui.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val movieId: Long = savedStateHandle.get("movieId")!!
    private val _movie = MutableStateFlow(Movie.Empty)
    val movie: StateFlow<Movie> = _movie

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _movie.value = movieRepository.getMovieById(movieId = movieId)
            }
        }
    }
}