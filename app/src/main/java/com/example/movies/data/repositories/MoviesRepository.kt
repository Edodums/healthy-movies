package com.example.movies.data.repositories

import androidx.paging.PagingData
import com.example.movies.ui.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}