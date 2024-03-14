package com.example.weatherforecastapplication.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.ui.ResultState
import com.example.weatherforecastapplication.ui.setting.SettingViewModel
import com.example.weatherforecastapplication.ui.setting.SettingViewModelFactory
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    private val hourlyWeatherAdapter=HourlyWeatherAdapter()
    private val dailyWeatherAdapter=DailyWeatherAdapter()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    @Inject lateinit var locationDao: LocationDao
    private val My_LOCATION_PERMISSION_ID = 5005
    @Inject lateinit var apiService: ApiService

  /*  private val homeViewModel: HomeViewModel by viewModels {

        HomeViewModelFactory(
            WeatherRepo.WeatherRepoImp(
                WeatherRemoteSource.WeatherRemoteSourceImp(apiService),
            WeatherLocalSource.WeatherLocalSourceImp(locationDao)), PreferenceManager(requireContext())
        )
    }*/
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory(PreferenceManager(requireContext()))
    }


    private fun observeLocationMode() {
        settingViewModel.locationMode.observe(viewLifecycleOwner) { mode ->
            when (mode) {
                "GPS" -> getLocation()
                "Map" -> {
                    val latLng = arguments?.getParcelable<LatLng>("latLng")
                    latLng?.latitude?.let {
                        latLng.longitude.let { it1 ->
                            viewModel.getWeatherData(it, it1,requireContext())
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        observeLocationMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner=this

        viewDataBinding.viewModel=viewModel
        observeLocationMode()
        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.weatherDataStateFlow.onEach { resultState->
            when (resultState) {
                is ResultState.Success -> {
                    viewDataBinding.homeProgressBar.visibility = View.GONE
                    val weatherData = resultState.locationModel.firstOrNull()
                    if (weatherData != null) {
                        initHourlyRecyclerView(weatherData)
                        initDailyRecyclerView(weatherData)
                    }
                }

                is ResultState.Error -> {
                    viewDataBinding.homeProgressBar.visibility=View.GONE
                    showToastMsg(requireContext(),resultState.error)
                    Log.e("Fragment", "Error: ${resultState.error}")
                }

                is ResultState.Loading -> {
                    viewDataBinding.homeProgressBar.visibility=View.VISIBLE

                    Log.d("Fragment", "Loading")
                }

                else -> {
                    viewDataBinding.homeProgressBar.visibility=View.GONE
                    Log.d("Fragment", "else")

                }
            }
        }
       /* viewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            initDailyRecyclerView(weatherData)
            initHourlyRecyclerView(weatherData)
        }*/
    }

    private fun initDailyRecyclerView(weatherData: WeatherDataEntity?) {
        dailyWeatherAdapter.submitList(weatherData?.dailyWeather)
        viewDataBinding.dailyRecyclerView.adapter=dailyWeatherAdapter
    }

    private fun initHourlyRecyclerView(weatherData: WeatherDataEntity?) {
        hourlyWeatherAdapter.submitList(weatherData?.hourlyWeather)
        viewDataBinding.recyclerView.adapter=hourlyWeatherAdapter
    }

    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocation()
            } else {
                Toast.makeText(requireContext(), "Please, Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == My_LOCATION_PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ), My_LOCATION_PERMISSION_ID
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val lastLocation = locationResult.lastLocation
                val lat = lastLocation?.latitude
                val lon = lastLocation?.longitude

                if (lat != null && lon != null) {
                    viewModel.getWeatherData(lat, lon,requireContext())
                }

                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }


    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): HomeViewModel {
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MAP && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<LatLng>(EXTRA_SELECTED_LOCATION)?.let { selectedLocation ->
                val lat = selectedLocation.latitude
                val lon = selectedLocation.longitude
                viewModel.getWeatherData(lat, lon,requireContext())
            }
        }
    }
    companion object {
        private const val REQUEST_CODE_MAP = 1001
        const val EXTRA_SELECTED_LOCATION = "selected_location"
    }


}


