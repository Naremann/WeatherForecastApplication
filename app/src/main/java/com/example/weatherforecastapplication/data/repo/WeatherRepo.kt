package com.example.weatherforecastapplication.data.repo


import android.content.Context
import com.example.weatherforecastapplication.NetworkUtils
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.model.getAddress
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WeatherRepo {
    suspend fun insertLocationToFav(weatherDataEntity: WeatherDataEntity)
    fun getAllFavLocations(): Flow<List<WeatherDataEntity>>
    suspend fun deleteLocationFromFav(weatherDataEntity: WeatherDataEntity)
    suspend fun getWeatherForecast(lat:Double,lon:Double,lang:String): WeatherDataEntity
    suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity)






    class WeatherRepoImp @Inject constructor(private val weatherRemoteSource: WeatherRemoteSource,
                                             private val weatherLocalSource: WeatherLocalSource,@ApplicationContext private val context: Context
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
            if (NetworkUtils.isNetworkAvailable(context)) {
                val weatherData = weatherRemoteSource.getWeatherForecast(lat, lon, lang)
                return WeatherDataEntity(
                    city = getAddress(lat,lon,context),
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
            else{
               return weatherLocalSource.getLatestLocationData()
            }
        }

        override suspend fun saveLatestLocationData(weatherDataEntity: WeatherDataEntity) {
            weatherLocalSource.saveLatestLocationData(weatherDataEntity)
        }


    }

}
