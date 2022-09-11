package com.example.movies.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movies.data.network.MoviesApiImpl
import com.example.movies.di.DatabaseModule
import com.example.movies.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshTrendingWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val response = MoviesApiImpl(NetworkModule.ktorHttpClient()).getMovies().result
            val database = DatabaseModule.provideAppDatabase(applicationContext)
            database.movies().insertAll(response.map { it.toDomain() })
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}