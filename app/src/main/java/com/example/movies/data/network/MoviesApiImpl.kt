package com.example.movies.data.network


import com.example.movies.data.entities.network.MoviesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class MoviesApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : MoviesApi {
    override suspend fun getMovies(page: Int): MoviesResponse {
        return httpClient.get {
            url {
                path("trending/movie/week")
                parameters.append("page", page.toString())
            }
        }.body()
    }
}