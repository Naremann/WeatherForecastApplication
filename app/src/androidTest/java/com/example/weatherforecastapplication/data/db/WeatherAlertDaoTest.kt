package com.example.weatherforecastapplication.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class WeatherAlertDaoTest {
    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()
    private lateinit var myDatabase:MyDatabase
    private lateinit var weatherAlertDao: WeatherAlertDao

    @Before
    fun setUp(){
        myDatabase= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext()
            ,MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        weatherAlertDao=myDatabase.getWeatherAlertDao()
    }

    @After
    fun closeDB(){
        myDatabase.close()
    }

    @Test
    fun addWeatherAlert_insertSingleAlert_alertIsInDatabase()= runBlockingTest{
       val weatherAlertEntity=WeatherAlertEntity(id = 1, hour = 2, minute = 20, alarmType = "Notification",
           isActive = true, duration = 2)
        weatherAlertDao.addWeatherAlert(weatherAlertEntity)
        val loadedWeatherDataEntity=myDatabase.getWeatherAlertDao().getAllWeatherAlerts().first()
        assertEquals(1,loadedWeatherDataEntity.size)
        assertThat(loadedWeatherDataEntity[0], CoreMatchers.`is`(weatherAlertEntity))
        assertThat(loadedWeatherDataEntity[0], CoreMatchers.notNullValue())
    }

    @Test
    fun getAllWeatherAlerts_insertMultipleAlerts_allAlertsAreRetrieved() = runBlockingTest {
        val alert1=WeatherAlertEntity(id = 1, hour = 2, minute = 20, alarmType = "Notification",
            isActive = true, duration = 2)
        val alert2=WeatherAlertEntity(id = 2, hour = 2, minute = 20, alarmType = "Alarm",
            isActive = true, duration = 2)

        weatherAlertDao.addWeatherAlert(alert1)
        weatherAlertDao.addWeatherAlert(alert2)

        val allAlerts = weatherAlertDao.getAllWeatherAlerts().first()

        assertEquals(2, allAlerts.size)
        assertEquals(alert1.alarmType, allAlerts[0].alarmType)
        assertEquals(alert1.hour, allAlerts[0].hour)
        assertEquals(alert2.isActive, allAlerts[1].isActive)
    }

    @Test
    fun deleteWeatherAlert_insertThenDeleteAlert_alertIsDeleted() = runBlockingTest {
        val alert=WeatherAlertEntity(id = 1, hour = 2, minute = 20, alarmType = "Notification",
            isActive = true, duration = 2)
        weatherAlertDao.addWeatherAlert(alert)
        val allAlertsBeforeDelete = weatherAlertDao.getAllWeatherAlerts().first()
        assertEquals(1, allAlertsBeforeDelete.size)
        weatherAlertDao.deleteWeatherAlert(alert)
        val allAlertsAfterDelete = weatherAlertDao.getAllWeatherAlerts().firstOrNull()
        assertThat(allAlertsAfterDelete, empty())
    }
}