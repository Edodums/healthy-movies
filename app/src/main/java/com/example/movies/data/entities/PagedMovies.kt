package com.example.movies.data.entities

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer


@Immutable
data class PagedMovies(val page: Int, val result: List<Movie>)


@Serializable
data class PagedMoviesResponse(
    @SerialName("page")
    val page: Int,
    @Serializable(with = MovieResponseListSerializer::class)
    @SerialName("results")
    val result: List<MovieResponse>
) {
    fun toUI(): PagedMovies {
        return PagedMovies(result = this.result.map { it.toUI() }, page = this.page)
    }
}

object MovieResponseListSerializer :
    JsonTransformingSerializer<List<MovieResponse>>(ListSerializer(MovieResponse.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) JsonArray(listOf(element)) else element
}