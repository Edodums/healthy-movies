package com.example.movies.data.repositories


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.data.db.MovieDB
import com.example.movies.data.network.MoviesApi
import com.example.movies.data.repositories.paging.MovieDBPagingSource
import com.example.movies.ui.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val db: MovieDB,
    private val api: MoviesApi
) : MoviesRepository {
    override fun getMovies(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                MovieDBPagingSource(api, db)
            }
        ).flow


    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}