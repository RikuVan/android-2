package fi.monad.asteroidradar.domain

import android.os.Parcelable
import fi.monad.asteroidradar.persistence.AsteroidEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable

fun List<Asteroid>.toEntity(): Array<AsteroidEntity> {
    return map { a ->
        AsteroidEntity(
            id = a.id,
            codename = a.codename,
            closeApproachDate = a.closeApproachDate,
            absoluteMagnitude = a.absoluteMagnitude,
            estimatedDiameter = a.estimatedDiameter,
            relativeVelocity = a.relativeVelocity,
            distanceFromEarth = a.distanceFromEarth,
            isPotentiallyHazardous = a.isPotentiallyHazardous
        )
    }.toTypedArray()
}