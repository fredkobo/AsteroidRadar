package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.NetworkAsteroidContainer
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AsteroidsRepository(private val database: AsteroidsDatabase) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    companion object {
        const val TAG = "AsteroidsRepository"
        const val SEVEN_DAYS_MILLIS = 604800000L
    }

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val todayDate = Date()
            val sevenDaysDate = todayDate.time + SEVEN_DAYS_MILLIS
            try {
                val asteroidsResponseString = AsteroidApi.retrofitService.getAsteroids(
                    dateFormat.format(todayDate),
                    dateFormat.format(sevenDaysDate),
                    API_KEY
                )
                val jsonObject = JSONObject(asteroidsResponseString)
                val asteroids = parseAsteroidsJsonResult(jsonObject)
                val networkAsteroidContainer = NetworkAsteroidContainer(asteroids)
                database.asteroidDao.insertAll(*networkAsteroidContainer.asDatabaseModel())
            } catch (e: Exception) {
                Log.e(TAG, e.printStackTrace().toString())
            }


        }
    }

}