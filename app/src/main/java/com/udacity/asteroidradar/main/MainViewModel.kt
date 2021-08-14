package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        val asteroid1 = Asteroid(1, "Sofia", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true)
        val asteroid2 = Asteroid(1, "Haley", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, false)
        val asteroid3 = Asteroid(1, "Samantha", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true)
        val asteroid4 = Asteroid(1, "Lela", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, false)
        val asteroid5 = Asteroid(1, "Lizzy", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true)
        val asteroid6 = Asteroid(1, "Zippy", "2021-05-11", 2400.0, 1200.0, 200.0, 12023.3, true)
        _asteroids.value = listOf(asteroid1, asteroid2, asteroid3, asteroid4, asteroid5, asteroid6)

        getPictureOfDay()
        //getAsteroids("2015-09-07", "2015-09-09")
    }

    fun onAsteroidNightClicked(asteroidId: Long) {
        TODO("Not yet implemented")
    }

    private fun getAsteroids(startDate: String, endDate: String) {
        viewModelScope.launch {
            val jsonResult = AsteroidApi.retrofitService.getAsteroids(startDate, endDate, API_KEY)
            _asteroids.value = parseAsteroidsJsonResult(jsonResult);
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDay.value = AsteroidApi.retrofitService.getPictureOfDay(API_KEY)
        }
    }
}