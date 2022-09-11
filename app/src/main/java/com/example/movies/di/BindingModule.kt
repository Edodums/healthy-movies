package com.example.movies.di

import com.example.movies.data.network.MoviesApi
import com.example.movies.data.network.MoviesApiImpl
import com.example.movies.data.repositories.MovieRepository
import com.example.movies.data.repositories.MovieRepositoryImpl
import com.example.movies.data.repositories.MoviesRepository
import com.example.movies.data.repositories.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindingModule {
    /**
     * Api Services
     */
    @Binds
    fun bindMovieApiService(moviesApi: MoviesApiImpl): MoviesApi

    /**
     * Repositories
     */
    @Binds
    fun bindMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}