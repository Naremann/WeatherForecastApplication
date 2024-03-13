package com.example.weatherforecastapplication.data.repo

import androidx.room.Dao
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.MyDatabase
import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSource
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSourceImp
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent


/*@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {

    @ViewModelScoped
    @Provides
    fun provideWeatherRepo(remoteSource: WeatherRemoteSource?): WeatherRepo? {
        return remoteSource?.let { WeatherRepo.WeatherRepoImp(it) }
    }

    @ViewModelScoped
    @Provides
    fun provideRemoteRepo(apiService: ApiService?): WeatherRemoteSource? {
        return apiService?.let { WeatherRemoteSource.WeatherRemoteSourceImp(it) }
    }
}*/
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRemoteRepo(apiService: ApiService?): WeatherRemoteSource? {
        return apiService?.let { WeatherRemoteSource.WeatherRemoteSourceImp(it) }
    }

    @Provides
    fun provideWeatherRepo(remoteSource: WeatherRemoteSource?, weatherLocalSource: WeatherLocalSource): WeatherRepo? {
        return remoteSource?.let { WeatherRepo.WeatherRepoImp(it, weatherLocalSource) }
    }

    @Provides
    fun provideWeatherLocalRepo(locationDao: LocationDao): WeatherLocalSource {
        return WeatherLocalSource.WeatherLocalSourceImp(locationDao)
    }

    @Provides
    fun provideWeatherAlertRepo(weatherAlertLocalSource: WeatherAlertLocalSource):WeatherAlertRepo{
        return WeatherAlertRepoImp(weatherAlertLocalSource)
    }

    @Provides
    fun provideWeatherAlertLocalSource(weatherAlertDao: WeatherAlertDao):WeatherAlertLocalSource{
        return WeatherAlertLocalSourceImp(weatherAlertDao)
    }
}

