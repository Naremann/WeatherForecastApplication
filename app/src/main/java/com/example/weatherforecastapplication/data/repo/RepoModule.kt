package com.example.weatherforecastapplication.data.repo

import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSource
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSourceImp
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


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
/*@Module
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
}*/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideRemoteRepo(weatherRemoteSourceImp:WeatherRemoteSource.WeatherRemoteSourceImp): WeatherRemoteSource?

   @Singleton
    @Binds
    abstract fun provideWeatherRepo(weatherRepoImp:WeatherRepo.WeatherRepoImp): WeatherRepo
    @Binds
    abstract fun provideWeatherLocalRepo(weatherLocalSource :WeatherLocalSource.WeatherLocalSourceImp):WeatherLocalSource

    @Binds
    abstract fun provideWeatherAlertRepo(weatherAlertRepo: WeatherAlertRepoImp):WeatherAlertRepo

    @Binds
    abstract fun provideWeatherAlertLocalSource(weatherAlertLocalSource: WeatherAlertLocalSourceImp):WeatherAlertLocalSource
}

