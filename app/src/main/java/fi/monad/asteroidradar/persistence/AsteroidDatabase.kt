package fi.monad.asteroidradar.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val DATABASE_NAME = "asteroids"

@Database(entities = [AsteroidEntity::class, PictureOfDayEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureDao: PictureOfDayDao

    companion object {
        @Volatile private var instance: AsteroidDatabase? = null

        fun getDatabase(context: Context): AsteroidDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        fun buildDatabase(context: Context): AsteroidDatabase {
            return Room.databaseBuilder(context, AsteroidDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

