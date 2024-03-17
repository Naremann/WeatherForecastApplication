package com.example.weatherforecastapplication.ui.fav

import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.FakeWeatherRepo
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runCurrent
import net.bytebuddy.matcher.ElementMatchers.`is`
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    private lateinit var weatherRepo: FakeWeatherRepo
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var testDispatcher: TestCoroutineDispatcher



    @Before
    fun setUp() {
        testDispatcher = TestCoroutineDispatcher()
        weatherRepo = FakeWeatherRepo()
        favoriteViewModel = FavoriteViewModel(weatherRepo)
    }

    @Test
    fun deleteFavLocation_insertLocationToFav_deleteFavLocation_locationIsDeletedSuccessfully() =
        runBlockingTest {
            val weatherDataEntity = WeatherDataEntity(
                city = "Cairo City",
                temp = "18",
                windSpeed = "10",
                humidity = "60",
                feelsLike = "28",
                pressure = "1010",
                clouds = "20",
                iconCode = "01d",
                hourlyWeather = emptyList(),
                dailyWeather = emptyList(),
                description = "Clear Sky"
            )
            favoriteViewModel.deleteFavLocation(weatherDataEntity)
            assertTrue(favoriteViewModel.favLocation.value is ResultState.Loading)
            testDispatcher.scheduler.advanceUntilIdle()
            assertTrue(favoriteViewModel.favLocation.value is ResultState.SuccessMsg)
        }


    @Test
    fun deleteFavLocationShouldResultInErrorStateWhenDeletionFails() = runBlockingTest {
        val weatherDataEntity = WeatherDataEntity(
            city = "Cairo City",
            temp = "18",
            windSpeed = "10",
            humidity = "60",
            feelsLike = "28",
            pressure = "1010",
            clouds = "20",
            iconCode = "01d",
            hourlyWeather = emptyList(),
            dailyWeather = emptyList(),
            description = "Clear Sky"
        )

        weatherRepo.simulateError(true)

        favoriteViewModel.deleteFavLocation(weatherDataEntity)
        assertTrue(favoriteViewModel.favLocation.value is ResultState.Loading)

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(favoriteViewModel.favLocation.value is ResultState.Error)

        val errorState = favoriteViewModel.favLocation.value as ResultState.Error
        assertEquals("Deletion Failed!", errorState.message)
    }


    @Test
    fun `getAllFavLocations emits loading then error`() = testDispatcher.runBlockingTest {
        // Given
        weatherRepo.simulateGetError(true)
        val errorMessage = "Fetch error"

        // When
        val resultList = mutableListOf<ResultState<List<WeatherDataEntity>>>()
        var loadingEmitted = false
        weatherRepo.getAllFavLocations().onStart {
            resultList.add(ResultState.Loading)
            loadingEmitted = true
        }.catch { e ->
            resultList.add(ResultState.Error(e.localizedMessage ?: "Unknown error"))
        }.collect { result ->
            resultList.add(ResultState.Success(result))
        }

        // Then
        assertTrue(loadingEmitted)
        assertEquals(2, resultList.size)
        assertTrue(resultList[0] is ResultState.Loading)
        assertTrue(resultList[1] is ResultState.Error)
        val errorState = resultList[1] as ResultState.Error
        assertEquals(errorMessage, errorState.message)
    }




    /*  @Test
      fun testNavigateToMapSelectionFragment() {
          // Given
          val navigator = StubNavigator()
          viewModel.navigator = navigator

          // When
          viewModel.navigateToMapSelectionFragment()

          // Then
          // Verify navigator called
          assertThat(navigator.isNavigateToMapSelectionFragmentCalled, `is`(true))
      }*/

}
