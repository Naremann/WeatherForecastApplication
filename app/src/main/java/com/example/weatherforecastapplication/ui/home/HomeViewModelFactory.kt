package com.example.weatherforecastapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.db.PreferenceManager
import com.example.weatherforecastapplication.repo.WeatherRepo


class HomeViewModelFactory(private val repo: WeatherRepo, private val preferenceManager: PreferenceManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo,preferenceManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.repo.WeatherRepo
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/*@Singleton
class ViewModelFactory @Inject constructor(
    private val creators: WeatherRepo.WeatherRepoImp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}*/
 */
