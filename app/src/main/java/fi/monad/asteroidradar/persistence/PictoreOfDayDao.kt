package fi.monad.asteroidradar.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureOfDayDao {
    @Query("select * from picture_of_day")
    fun getPictureOfTheDay(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pictureEntity: PictureOfDayEntity)

    @Query("Delete from picture_of_day")
    fun clear()
}