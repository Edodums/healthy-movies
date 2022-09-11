package com.example.movies.ui.models

import androidx.compose.runtime.Immutable


@Immutable
data class Movie(val id: Long = -1L, val page: Int = 1, val title: String = "", val plot: String = "", val posterUrl: String = "") {
    companion object {
        val Empty = Movie()
    }
}