package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.data.db.MovieDB
import com.example.movies.data.db.MovieDao
import com.example.movies.data.db.PagingDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MovieDB =
         Room.databaseBuilder(
            context,
            MovieDB::class.java,
            "movie-db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviesDao(db: MovieDB): MovieDao {
        return db.movies()
    }

    @Provides
    fun providePagingDataDao(db: MovieDB): PagingDataDao {
        return db.pagingData()
    }

}