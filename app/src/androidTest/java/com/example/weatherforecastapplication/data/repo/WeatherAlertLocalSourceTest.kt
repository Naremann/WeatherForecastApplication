package com.example.weatherforecastapplication.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherforecastapplication.data.db.MyDatabase
import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSourceImp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class WeatherAlertLocalSourceTest {
    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()
    private lateinit var weatherAlertDao: WeatherAlertDao
    private lateinit var database: MyDatabase
    private lateinit var weatherAlertLocalSource: WeatherAlertLocalSourceImp


    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build()

        weatherAlertDao = database.getWeatherAlertDao()
        weatherAlertLocalSource = WeatherAlertLocalSourceImp(weatherAlertDao)
    }

    @After
    fun closeDB() {
        database.close()
    }

    @Test
    fun testAddWeatherAlert() = runBlocking {
        val weatherAlert = WeatherAlertEntity(
            hour = 10,
            minute = 30,
            duration = 60,
            alarmType = "Test",
            isActive = true
        )

        weatherAlertLocalSource.addWeatherAlert(weatherAlert)

        val allWeatherAlerts = weatherAlertLocalSource.getAllWeatherAlerts().first()
        assertThat(allWeatherAlerts. map { it.copy(id = 0) },
            `is`(weatherAlert))
       // assertThat(allWeatherAlerts.contains(weatherAlert), `is`(true))
    }

    @Test
    fun testDeleteWeatherAlert() = runBlocking {
        val weatherAlert = WeatherAlertEntity(
            hour = 10,
            minute = 30,
            duration = 60,
            alarmType = "Test",
            isActive = true
        )
        weatherAlertLocalSource.addWeatherAlert(weatherAlert)
        weatherAlertLocalSource.deleteWeatherAlert(weatherAlert)
        val allWeatherAlerts = weatherAlertLocalSource.getAllWeatherAlerts().first()
        assertThat(allWeatherAlerts.contains(weatherAlert), `is`(false))
    }

    @Test
    fun testGetAllWeatherAlerts() = runBlocking {
        val weatherAlert1 = WeatherAlertEntity(
            hour = 10,
            minute = 30,
            duration = 60,
            alarmType = "Test1",
            isActive = true
        )
        val weatherAlert2 = WeatherAlertEntity(
            hour = 12,
            minute = 0,
            duration = 30,
            alarmType = "Test2",
            isActive = false
        )
        weatherAlertLocalSource.addWeatherAlert(weatherAlert1)
        weatherAlertLocalSource.addWeatherAlert(weatherAlert2)
        val allWeatherAlerts = weatherAlertLocalSource.getAllWeatherAlerts().first()
        assertThat(allWeatherAlerts.map { it.copy(id = 0) },
            `is`(listOf(weatherAlert1.copy(id = 0), weatherAlert2.copy(id = 0)))
        )
    }

}