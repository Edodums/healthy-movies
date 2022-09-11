package com.example.movies.data.repositories.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.example.movies.data.db.MovieDB
import com.example.movies.data.db.MovieDao
import com.example.movies.data.db.PagingDataDao
import com.example.movies.data.entities.local.PagingData
import com.example.movies.data.network.MoviesApi
import com.example.movies.ui.models.Movie

private const val STARTING_PAGE_INDEX = 1

/**
 *
 */
class MovieDBPagingSource(
    private val service: MoviesApi,
    private val db: MovieDB
) : PagingSource<Int, Movie>() {
    private val movieDao: MovieDao = db.movies()
    private val pagingDataDao: PagingDataDao = db.pagingData()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            var exists = false
            db.withTransaction {
                exists = movieDao.existPage(page)
            }

            val pair = getPair(exists, page)
            val movies = pair.first
            val totalPages = pair.second
            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == totalPages) null else page + 1
            )

        } catch (exception: Exception) {
            Log.e("NOT LOADED", exception.message.toString())
            LoadResult.Error(exception)
        }
    }

    private suspend fun getPair(
        exists: Boolean,
        page: Int
    ): Pair<List<Movie>, Int> {
        var totalPagesTemp = 0
        var moviesTemp: List<Movie> = emptyList()

        if (exists) {
            db.withTransaction {
                totalPagesTemp = getLocalTotalPages()
                moviesTemp = movieDao.getAllMoviesByPage(page).map { it.toUI() }
            }
        } else {
            val response = service.getMovies(page)
            totalPagesTemp = response.totalPages

            db.withTransaction {
                movieDao.insertAll(response.result.map { it.toDomain() })
                pagingDataDao.insert(PagingData(0, response.totalPages, response.totalResults))
            }

            moviesTemp = response.result.map { it.toUI() }
        }

        return Pair(moviesTemp, totalPagesTemp)
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }


    private fun getLocalTotalPages(): Int {
        return pagingDataDao.getData().totalPages
    }
}