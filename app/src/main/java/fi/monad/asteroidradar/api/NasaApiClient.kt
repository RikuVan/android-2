package fi.monad.asteroidradar.api

import fi.monad.asteroidradar.Constants
import retrofit2.Response
import java.lang.Exception

class NasaApiClient(private val api: NasaApiServiceI) {

    suspend fun getAsteroids(start: String, end: String, apiKey: String = Constants.NASA_API_KEY) =
        safeApiCall {
            api.getAsteroids(start, end, apiKey)
        }

    suspend fun getPictureOfTheDay(apiKey: String = Constants.NASA_API_KEY) = safeApiCall {
        api.getPictureOfTheDay(apiKey)
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): Result<T> {
        return try {
            Result.ok(apiCall.invoke())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
