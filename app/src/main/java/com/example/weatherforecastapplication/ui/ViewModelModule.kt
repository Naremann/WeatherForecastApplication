/*
package com.example.weatherforecastapplication.ui

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

*/
/*@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}*//*



@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelModule {

    //@Binds
    @Provides
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory{
        return factory
    }
}

*/
package com.example.weatherforecastapplication.ui

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ViewModelModule {

    /*@Provides
    fun provideWeatherRepo(weatherRemoteSource: WeatherRemoteSource): WeatherRepo {
        return WeatherRepo.WeatherRepoImp(weatherRemoteSource)
    }

    @Provides
    fun provideHomeViewModel(weatherRepo: WeatherRepo): HomeViewModel {
        return HomeViewModel(weatherRepo)
    }

    @Provides
    fun provideViewModelMap(homeViewModel: HomeViewModel): Map<Class<out ViewModel>, Provider<ViewModel>> {
        return mapOf(
            HomeViewModel::class.java to Provider<ViewModel> { homeViewModel }
        )
    }

   *//* @Provides
    fun provideViewModelFactory(creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }*//*

    @Provides
    @IntoMap
    @StringKey("HomeViewModel")
    fun provideHomeViewModelFactory(viewModel: HomeViewModel): ViewModel {
        return viewModel
    }*/
}





