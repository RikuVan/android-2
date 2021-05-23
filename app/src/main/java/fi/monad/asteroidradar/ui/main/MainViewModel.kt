package fi.monad.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import fi.monad.asteroidradar.domain.Asteroid
import fi.monad.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

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
}
