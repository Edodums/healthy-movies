package com.example.movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.entities.local.PagingData

@Dao
interface PagingDataDao {
    @Query("SELECT * FROM paging_data LIMIT 1")
    fun getData(): PagingData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pagingData: PagingData)
}