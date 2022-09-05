package com.example.movies.data.entities

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * UI Entity
 */
@Immutable
data class Movie(val id: Long, val title: String, val plot: String, val posterUrl: String)

/**
 * Network Entity
 */
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
    fun toUI() : Movie {
        return Movie(
            id = this.id.toLong(),
            title = this.title,
            plot = this.plot,
            posterUrl = this.posterUrl
        )
    }
}
