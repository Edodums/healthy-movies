package com.example.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.entities.local.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE id=:movieId")
    suspend fun getMovieById(movieId: Long): Movie

    @Query("SELECT * from movies where page=:page")
    fun getAllMoviesByPage(page: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Movie>)

    @Query("SELECT EXISTS(SELECT DISTINCT page FROM movies WHERE page=:page)")
    fun existPage(page: Int): Boolean

    @Query("DELETE FROM movies WHERE page=:page")
    fun deleteByPage(page: Int)
}