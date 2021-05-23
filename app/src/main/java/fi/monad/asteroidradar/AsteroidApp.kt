package fi.monad.asteroidradar

import android.app.Application
import timber.log.Timber

class AsteroidApp : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}