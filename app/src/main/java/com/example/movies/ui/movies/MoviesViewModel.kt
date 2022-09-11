package com.example.movies.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.ui.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    fun getMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            moviesRepository.getMovies().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}