package com.example.movies.data.repositories

import com.example.movies.ui.models.Movie

interface MovieRepository {
   suspend fun getMovieById(movieId: Long): Movie
}