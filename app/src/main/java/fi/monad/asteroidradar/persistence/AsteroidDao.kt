package fi.monad.asteroidradar.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Query("select * from asteroid order by date(closeApproachDate) asc")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("select * from asteroid where closeApproachDate = :date")
    fun getTodaysAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("select * from asteroid where closeApproachDate between :startDate and :endDate order by date(closeApproachDate) asc")
    fun getWeeklyAsteroids(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("delete from asteroid where closeApproachDate < :date")
    suspend fun removeOldAsteroids(date: String)
}