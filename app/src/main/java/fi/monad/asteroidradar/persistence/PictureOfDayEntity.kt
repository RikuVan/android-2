package fi.monad.asteroidradar.persistence

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import fi.monad.asteroidradar.domain.PictureOfDay
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "picture_of_day")
@Parcelize
data class PictureOfDayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val mediaType: String,
    val title: String,
    val url: String
) : Parcelable

fun PictureOfDayEntity.toDomain(): PictureOfDay {
    return PictureOfDay(
        url = this.url,
        title = this.title,
        mediaType = this.mediaType
    )
}