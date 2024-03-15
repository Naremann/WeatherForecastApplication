package com.example.weatherforecastapplication.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.MyDatabase
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class WeatherLocalSourceTest {

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var locationDao: LocationDao
    private lateinit var weatherLocalSource: WeatherLocalSource
    private lateinit var myDatabase: MyDatabase

    @Before
    fun setUp(){
        myDatabase=Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),MyDatabase::class.java
        ).allowMainThreadQueries().build()
        locationDao=myDatabase.getLocationDao()
        weatherLocalSource=WeatherLocalSource.WeatherLocalSourceImp(locationDao)
    }
    @After
    fun closeDB(){
        myDatabase.close()
    }

    @Test
    fun insertLocationToFav_getTheLocation()= runBlocking{
        val weatherDataEntity= WeatherDataEntity(id = 1,city = "city", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear")
        weatherLocalSource.insertLocationToFav(weatherDataEntity)
        val favLocation=weatherLocalSource.getAllFavLocations().first()
        assertThat(favLocation[0],`is`(weatherDataEntity))
    }

    @Test
    fun deleteLocationFromFav_deleteLocationFromFavWithListOfSize3_listWithSize2()= runBlocking {
        val favList= listOf(WeatherDataEntity(id = 1,city = "city One", temp = "18.0", windSpeed ="1118"
        ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
        , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"),
            WeatherDataEntity(id = 2,city = "city Two", temp = "18.0", windSpeed ="1118"
        ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
        , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"),
            WeatherDataEntity(id = 3,city = "city", temp = "18.0", windSpeed ="1118"
        ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
        , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"))

        favList.forEach { weatherDataEntity -> weatherLocalSource.insertLocationToFav(weatherDataEntity) }
        weatherLocalSource.deleteLocationFromFav(favList[0])
        val allFavs=weatherLocalSource.getAllFavLocations().first()
        assertEquals(2,allFavs.size)
    }

    @Test
    fun getAllFavLocations_insertMultipleLocations_returnAllAddedLocations()= runBlocking {
        val favList= listOf(WeatherDataEntity(id = 1,city = "city One", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"),
            WeatherDataEntity(id = 2,city = "city Two", temp = "18.0", windSpeed ="1118"
                ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
                , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"),
            WeatherDataEntity(id = 3,city = "city", temp = "18.0", windSpeed ="1118"
                ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
                , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"))
        favList.forEach { weatherDataEntity -> weatherLocalSource.insertLocationToFav(weatherDataEntity) }
        val allFavs=weatherLocalSource.getAllFavLocations().first()
        assertThat(allFavs[0].city,`is`(favList[0].city))
        assertThat(allFavs,`is`(favList))
        assertEquals(allFavs.size,3)
    }
}
