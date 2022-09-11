package com.example.movies.data.repositories

import com.example.movies.data.db.MovieDB
import com.example.movies.ui.models.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val moviesDB: MovieDB
) : MovieRepository {
    override suspend fun getMovieById(movieId: Long): Movie =
        moviesDB.movies().getMovieById(movieId).toUI()
}