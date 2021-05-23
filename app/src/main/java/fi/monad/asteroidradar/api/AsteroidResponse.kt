package fi.monad.asteroidradar.api

import fi.monad.asteroidradar.domain.Asteroid


data class AsteroidResponse(
    val element_count: Int = 0,
    val links: Links,
    val near_earth_objects: Map<String, List<AsteroidItem>>
)

fun AsteroidResponse.toAsteroidList(): List<Asteroid> {
    return near_earth_objects.flatMap { e ->
        e.value.map { a ->
            val closeApproachData = a.close_approach_data?.first()
            Asteroid(
                id = a.id,
                codename = a.name,
                closeApproachDate = closeApproachData.close_approach_date,
                absoluteMagnitude = a.absolute_magnitude_h,
                estimatedDiameter = a.estimated_diameter.kilometers.estimated_diameter_max,
                relativeVelocity = closeApproachData.relative_velocity.kilometers_per_second,
                distanceFromEarth = closeApproachData.miss_distance.kilometers,
                isPotentiallyHazardous = a.is_potentially_hazardous_asteroid,
            )
        }
    }
}

data class AsteroidItem(
    val estimated_diameter: EstimatedDiameter,
    val neo_reference_id: String = "",
    val nasa_jpl_url: String = "",
    val is_potentially_hazardous_asteroid: Boolean = false,
    val is_sentry_object: Boolean = false,
    val name: String = "",
    val absolute_magnitude_h: Double = 0.0,
    val links: Links,
    val id: Long,
    val close_approach_data: List<CloseApproachDataItem> = emptyList()
)


data class Meters(
    val estimated_diameter_max: Double = 0.0,
    val estimated_diameter_min: Double = 0.0
)


data class Miles(
    val estimated_diameter_max: Double = 0.0,
    val estimated_diameter_min: Double = 0.0
)


data class MissDistance(
    val astronomical: Double = 0.0,
    val kilometers: Double = 0.0,
    val lunar: Double = 0.0,
    val miles: Double = 0.0
)


data class CloseApproachDataItem(
    val relative_velocity: RelativeVelocity,
    val orbiting_body: String = "",
    val close_approach_date: String = "",
    val epoch_date_close_approach: Long = 0,
    val close_approach_date_full: String = "",
    val miss_distance: MissDistance
)


data class Kilometers(
    val estimated_diameter_max: Double = 0.0,
    val estimated_diameter_min: Double = 0.0
)


data class Links(
    val next: String = "",
    val prev: String = "",
    val self: String = ""
)


data class RelativeVelocity(
    val kilometers_per_hour: Double = 0.0,
    val kilometers_per_second: Double = 0.0,
    val miles_per_hour: Double = 0.0
)


data class Feet(
    val estimated_diameter_max: Double = 0.0,
    val estimated_diameter_min: Double = 0.0
)


data class EstimatedDiameter(
    val feet: Feet,
    val kilometers: Kilometers,
    val meters: Meters,
    val miles: Miles
)


