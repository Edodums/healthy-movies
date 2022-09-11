package com.example.movies.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.ui.models.Movie

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Long,
    val page: Int = 1,
    val title: String,
    val plot: String,
    val posterUrl: String
) {
    fun toUI(): Movie {
        return Movie(
            id = this.id,
            page = this.page,
            title = this.title,
            plot = this.plot,
            posterUrl = BASE_POSTER_URL + this.posterUrl
        )
    }

    companion object {
        private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500"
    }
}