package com.example.weatherforecastapplication.repo

import com.example.weatherforecastapplication.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
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

   // @ViewModelScoped
    @Provides
    fun provideRemoteRepo(apiService: ApiService?): WeatherRemoteSource? {
        return apiService?.let { WeatherRemoteSource.WeatherRemoteSourceImp(it) }
    }

    //@ViewModelScoped
    @Provides
    fun provideWeatherRepo(remoteSource: WeatherRemoteSource?): WeatherRepo? {
        return remoteSource?.let { WeatherRepo.WeatherRepoImp(it) }
    }
}

