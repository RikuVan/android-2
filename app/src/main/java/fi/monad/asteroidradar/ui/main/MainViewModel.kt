package fi.monad.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import fi.monad.asteroidradar.R
import fi.monad.asteroidradar.domain.Asteroid
import fi.monad.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    application: Application,
    private val repo: AsteroidRepository
) : AndroidViewModel(application) {

    val pictureOfTheDay = repo.pictureOfTheDay
    val asteroids = repo.asteroids
    val loading = repo.loadingAsteroids

    init {
        viewModelScope.launch {
            repo.refreshPictureOfTheDay()
            repo.refreshAsteroids()
        }
    }

    fun onFilterSelect(itemId: Int) {
        viewModelScope.launch {
            when (itemId) {
                R.id.show_all_menu -> repo.getAllAsteroids()
                R.id.show_today_menu -> repo.getTodaysAsteroids()
                R.id.show_week_menu -> repo.getWeeksAsteroids()
            }
        }
    }
}
