package com.example.movies.data.network


import com.example.movies.data.entities.network.MoviesResponse

interface MoviesApi {
    suspend fun getMovies(page: Int = 1): MoviesResponse
}