package fi.monad.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fi.monad.asteroidradar.persistence.AsteroidDatabase
import fi.monad.asteroidradar.repository.AsteroidRepository

class ViewModelFactory(
    private val application: Application,
    private val repo: AsteroidRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, repo) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}