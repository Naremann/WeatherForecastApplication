package com.example.weatherforecastapplication.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
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
class LocationDaoTest {

    @get:Rule
    var instantTaskExecutorRule= InstantTaskExecutorRule()
    private lateinit var myDatabase:MyDatabase
    private lateinit var locationDao: LocationDao

    @Before
    fun setUp(){
        myDatabase=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext()
        ,MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        locationDao=myDatabase.getLocationDao()
    }

    @After
    fun closeDB(){
        myDatabase.close()
    }

    @Test
    fun insertLocationToFav_insertSingleLocation_locationDataIsInDatabase()= runBlockingTest{
        val weatherDataEntity=WeatherDataEntity(id = 1,city = "city", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear")
        locationDao.insertLocationToFav(weatherDataEntity)
        val loadedWeatherDataEntity=myDatabase.getLocationDao().getAllFavLocations().first()
        assertEquals(1,loadedWeatherDataEntity.size)
        assertThat(loadedWeatherDataEntity[0], `is`(weatherDataEntity))
        assertThat(loadedWeatherDataEntity[0],notNullValue())
    }

    @Test
    fun deleteLocationFromFav_insertThenDeleteLocation_LocationDataIsDeleted()= runBlockingTest {
        val weatherDataEntity=WeatherDataEntity(id = 1,city = "city", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear")
        locationDao.insertLocationToFav(weatherDataEntity)
        val locationsDataBeforeDelete=locationDao.getAllFavLocations().first()
        assertEquals(1,locationsDataBeforeDelete.size)
        locationDao.deleteLocationFromFav(weatherDataEntity)
        val locationsDataAfterDelete=locationDao.getAllFavLocations().firstOrNull()
        assertThat(locationsDataAfterDelete, empty())
    }

    @Test
    fun getAllFavLocations_insertMultipleLocation_allLocationsAreRetrieved()= runBlockingTest {
        val favoriteLocations = listOf(WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"
        ),
            WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
                ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
                , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"))
        favoriteLocations.forEach { locationDao.insertLocationToFav(it) }
        assertEquals(2,favoriteLocations.size)
    }



}