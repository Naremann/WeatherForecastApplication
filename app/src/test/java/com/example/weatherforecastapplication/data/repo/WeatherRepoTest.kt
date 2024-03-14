package com.example.weatherforecastapplication.data.repo

import android.service.autofill.FieldClassification.Match
import com.example.weatherforecastapplication.data.model.DailyItem
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.local.FakeWeatherLocal
import com.example.weatherforecastapplication.data.repo.remote.FakeWeatherRemote
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.internal.matchers.Matches
import java.util.Optional.empty

class WeatherRepoTest {

    private lateinit var weatherRepo:WeatherRepo
    private lateinit var weatherRemoteSource: FakeWeatherRemote
    private lateinit var weatherLocalSource: FakeWeatherLocal

    @Before
    fun setUp(){
        weatherLocalSource= FakeWeatherLocal()
        weatherRemoteSource= FakeWeatherRemote()
        weatherRepo=WeatherRepo.WeatherRepoImp(weatherRemoteSource,weatherLocalSource)
    }

    @Test fun insertLocationToFav_WeatherItem_sizeIsOne()= runBlocking{
        val weatherDataEntity =  WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"
        )
        weatherRepo.insertLocationToFav(weatherDataEntity)
        val allFavorites=weatherRepo.getAllFavLocations().first()
        assertEquals(1,allFavorites.size)
        assertEquals(weatherDataEntity,allFavorites[0])
    }

    @Test
    fun deleteLocationFromFav_deleteWeatherItemWithListSizeOfTwo_sizeIsOne()= runBlocking{
        val weatherDataEntityOne =  WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"
        )
        val weatherDataEntityTwo =  WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"
        )
        weatherRepo.insertLocationToFav(weatherDataEntityOne)
        weatherRepo.insertLocationToFav(weatherDataEntityTwo)
        val favList=weatherRepo.getAllFavLocations().first()
        assertEquals(2, favList.size)
        weatherRepo.deleteLocationFromFav(weatherDataEntityOne)
        assertEquals(1,favList.size)
    }

    @Test
    fun getAllFavLocations()= runBlocking {
        val favoriteLocations = listOf(WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"
        ),
         WeatherDataEntity(city = "cairo", temp = "18.0", windSpeed ="1118"
            ,humidity = "80%", feelsLike = "40%", pressure = "20", clouds = "0%", iconCode = "0nd"
            , hourlyWeather = emptyList(), dailyWeather = emptyList(), description ="Clear"))
        favoriteLocations.forEach { weatherLocalSource.insertLocationToFav(it) }
        val allFavLocations = weatherRepo.getAllFavLocations().first()
        assertThat(allFavLocations, `is`(favoriteLocations))
    }


}