package com.example.weatherforecastapplication.data.repo


import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherRepo {
    suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity)
    fun getAllFavLocations(): Flow<List<WeatherDataEntity>>
    suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity)
    suspend fun getWeatherForecast(lat:Double,lon:Double,lang:String): WeatherDataEntity





    class WeatherRepoImp @Inject constructor(private val weatherRemoteSource: WeatherRemoteSource,
                                             private val weatherLocalSource: WeatherLocalSource
    ): WeatherRepo {


        override suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity) {
            weatherLocalSource.insertLocationToFav(weatherDataEntity)
        }

        override fun getAllFavLocations(): Flow<List<WeatherDataEntity>> {
            return weatherLocalSource.getAllFavLocations()
        }

        override suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity) {
            weatherLocalSource.deleteLocationFromFav(weatherDataEntity)
        }

        override suspend fun getWeatherForecast(
            lat: Double,
            lon: Double,
            lang: String
        ): WeatherDataEntity {
            val weatherData = weatherRemoteSource.getWeatherForecast(lat, lon, lang)
            return WeatherDataEntity(
                city = "city",
                temp = weatherData.current?.temp.toString(),
                windSpeed = weatherData.current?.windSpeed.toString(),
                humidity = weatherData.current?.humidity.toString(),
                feelsLike =
                weatherData.current?.feelsLike.toString(),
                pressure = weatherData.current?.pressure.toString(),
                clouds = weatherData.current?.clouds.toString(),
                iconCode = weatherData.current?.weather?.get(0)?.icon,
                hourlyWeather = weatherData.hourly,
                dailyWeather = weatherData.daily,
                description = weatherData.current?.weather?.get(0)?.description
            )
        }


    }

}
