package com.example.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.entities.local.Movie
import com.example.movies.data.entities.local.PagingData

@Database(
    entities = [Movie::class, PagingData::class],
    version = 23,
    exportSchema = false
)
abstract class MovieDB : RoomDatabase() {
    abstract fun movies(): MovieDao
    abstract fun pagingData(): PagingDataDao
}
