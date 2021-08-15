package com.udacity.asteroidradar.main

import android.util.Log
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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
        const val SEVEN_DAYS_MILLIS = 604800000L
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        getPictureOfDay()
        val todayDate = Date()
        val sevenDaysDate = todayDate.time + SEVEN_DAYS_MILLIS
        getAsteroids(dateFormat.format(todayDate), dateFormat.format(sevenDaysDate))
    }

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail
        get() = _navigateToAsteroidDetail

    fun onAsteroidNightClicked(asteroidId: Asteroid) {
        _navigateToAsteroidDetail.value = asteroidId
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }

    private fun getAsteroids(startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                val responseBody =
                    AsteroidApi.retrofitService.getAsteroids(startDate, endDate, API_KEY)
                val jsonObject = JSONObject(responseBody.string())
                _asteroids.value = parseAsteroidsJsonResult(jsonObject)
            } catch (e: Exception) {
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = AsteroidApi.retrofitService.getPictureOfDay(API_KEY)
            } catch (e: Exception) {
                e.message?.let { Log.d(TAG, it) }
            }
        }
    }
}