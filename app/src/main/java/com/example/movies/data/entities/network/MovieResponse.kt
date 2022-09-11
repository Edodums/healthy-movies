package com.example.movies.data.entities.network

import com.example.movies.ui.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.movies.data.entities.local.Movie as LocalMovie


@Serializable
data class MovieResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val plot: String,
    @SerialName("poster_path")
    val posterUrl: String
) {
    fun toDomain(): LocalMovie {
        return LocalMovie(
            id = this.id.toLong(),
            title = this.title,
            plot = this.plot,
            posterUrl = BASE_POSTER_URL + this.posterUrl
        )
    }

    fun toUI(): Movie {
        return Movie(
            id = this.id.toLong(),
            title = this.title,
            plot = this.plot,
            posterUrl = BASE_POSTER_URL + this.posterUrl
        )
    }

    companion object {
        private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500"
    }
}