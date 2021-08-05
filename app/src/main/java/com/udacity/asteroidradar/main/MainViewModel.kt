package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        val asteroid1 = Asteroid(1, "Sofia", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true )
        val asteroid2 = Asteroid(1, "Haley", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, false )
        val asteroid3 = Asteroid(1, "Samantha", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true )
        val asteroid4 = Asteroid(1, "Lela", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, false )
        val asteroid5 = Asteroid(1, "Lizzy", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true )
        val asteroid6 = Asteroid(1, "Zippy", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true )
        _asteroids.value = listOf(asteroid1, asteroid2, asteroid3, asteroid4, asteroid5, asteroid6)
    }

    fun onAsteroidNightClicked(asteroidId: Long) {
        TODO("Not yet implemented")
    }
}