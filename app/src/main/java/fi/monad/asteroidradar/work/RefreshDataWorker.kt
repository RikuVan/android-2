package fi.monad.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import fi.monad.asteroidradar.api.NasaApiClient
import fi.monad.asteroidradar.api.getApiServiceImpl
import fi.monad.asteroidradar.persistence.AsteroidDatabase
import fi.monad.asteroidradar.repository.AsteroidRepository
import java.lang.Exception

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params)  {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val repository = AsteroidRepository(
            NasaApiClient(getApiServiceImpl()),
            AsteroidDatabase.getDatabase(applicationContext)
        )
        return try {
            repository.refreshAsteroids()
            repository.refreshPictureOfTheDay()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}