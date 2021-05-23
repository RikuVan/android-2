package fi.monad.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fi.monad.asteroidradar.Constants
import fi.monad.asteroidradar.domain.Asteroid
import fi.monad.asteroidradar.domain.PictureOfDay
import fi.monad.asteroidradar.api.NasaApiClient
import fi.monad.asteroidradar.api.map
import fi.monad.asteroidradar.api.parseAsteroidsJsonResult
import fi.monad.asteroidradar.domain.toEntity
import fi.monad.asteroidradar.persistence.AsteroidDatabase
import fi.monad.asteroidradar.persistence.toDomain
import fi.monad.asteroidradar.utils.toFormattedDatePlus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepository(
    val apiClient: NasaApiClient,
    private val db: AsteroidDatabase
    ) {

    val pictureOfTheDay: LiveData<PictureOfDay> = Transformations.map(db.pictureDao.getPictureOfTheDay()) {
        it?.toDomain()
    }

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(db.asteroidDao.getAsteroids()) {
        it?.toDomain()
    }

    suspend fun getAsteroids(start: String, end: String): List<Asteroid>? {
        val result = apiClient.getAsteroids(start, end)

        val parsedResult = result.map {
            parseAsteroidsJsonResult(JSONObject(it.body()))
        }

        if (parsedResult.ok) {
            db.asteroidDao.insertAll(*parsedResult.body.toEntity())
        }

        return parsedResult.body
    }

    suspend fun getPictureOfTheDay(): PictureOfDay? {
        val result = apiClient.getPictureOfTheDay()
        if (result.failed or !result.ok) {
            return null
        }
        return result.body
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidsData = getAsteroids(
                start = Constants.API_QUERY_DATE_FORMAT.toFormattedDatePlus(0),
                end = Constants.API_QUERY_DATE_FORMAT.toFormattedDatePlus(7)
            )
            if (asteroidsData != null) Timber.d("ok") // database.asteroidDao.insertAll(*parsedAsteroids.toDatabaseModel())
        }
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            val result = apiClient.getPictureOfTheDay()
            if (result.ok) {
                db.pictureDao.insertAll(result.body.toEntity())
            }
        }
    }
}

