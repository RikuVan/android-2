package fi.monad.asteroidradar.domain

import com.squareup.moshi.Json
import fi.monad.asteroidradar.persistence.PictureOfDayEntity

data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDay.toEntity(): PictureOfDayEntity {
    return PictureOfDayEntity(
        url = url,
        title = title,
        mediaType = mediaType
    )
}
