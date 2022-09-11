package com.example.movies.data.entities.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonTransformingSerializer

@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int,
    @Serializable(with = MovieResponseListSerializer::class)
    @SerialName("results")
    val result: List<MovieResponse>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

object MovieResponseListSerializer :
    JsonTransformingSerializer<List<MovieResponse>>(ListSerializer(MovieResponse.serializer())) {
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) JsonArray(listOf(element)) else element
}