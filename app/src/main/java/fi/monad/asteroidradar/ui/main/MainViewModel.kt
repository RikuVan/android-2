package fi.monad.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import fi.monad.asteroidradar.domain.Asteroid
import fi.monad.asteroidradar.Constants
import fi.monad.asteroidradar.domain.PictureOfDay
import fi.monad.asteroidradar.persistence.AsteroidDatabase
import fi.monad.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import fi.monad.asteroidradar.utils.toFormattedDatePlus
import timber.log.Timber

class MainViewModel(
    application: Application,
    private val repo: AsteroidRepository
) : AndroidViewModel(application) {

    private val _currentAsteroid = MutableLiveData<Asteroid>()
    val currentAsteroid: LiveData<Asteroid>
        get() = _currentAsteroid

    val pictureOfTheDay = repo.pictureOfTheDay
    val asteroids = repo.asteroids

    init {
        viewModelScope.launch {
            repo.refreshPictureOfTheDay()
            repo.refreshAsteroids()
        }
    }

    fun onClickAsteroid(asteroid: Asteroid) {
        _currentAsteroid.value = asteroid
    }
}
