package com.example.movies.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "paging_data")
data class PagingData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val totalPages: Int,
    val totalResults: Int
)
