package com.example.weatherforecastapplication.ui.home

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherforecastapplication.LanguageUtils
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.model.weatherIconResourceId
import com.example.weatherforecastapplication.ui.ResultState
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val hourlyWeatherAdapter = HourlyWeatherAdapter()
    private val dailyWeatherAdapter = DailyWeatherAdapter()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val My_LOCATION_PERMISSION_ID = 5005
    private var latLng: LatLng? = null
    @Inject  lateinit var preferenceManager: PreferenceManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated, HOME: "+ preferenceManager.getLocationMode())
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this

        if(preferenceManager.getLocationMode() != "Map") {
            getLocation()
        }
        observeLatLng()
        observeStateFlow()
     //   LanguageUtils.setDefaultLanguage(preferenceManager,requireContext())

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach: ", )
        LanguageUtils.setDefaultLanguage(preferenceManager, context)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        observeLatLng()
    }


    override fun onResume() {
        super.onResume()
        observeLatLng()
    }


   /* override fun onStart() {
        super.onStart()
        observeLatLng()

    }*/


    private fun observeLatLng() {
        if (preferenceManager.getLocationMode()=="GPS"){
            getLocation()
        }
        val savedLatLng = preferenceManager.getLatLng()
        if (savedLatLng != null) {
            latLng = savedLatLng
            viewModel.getWeatherData(savedLatLng.latitude, savedLatLng.longitude)
        }
    }


    private fun observeStateFlow() {
        viewModel.weatherDataStateFlow.onEach { resultState ->
            when (resultState) {
                is ResultState.Success -> {
                    viewDataBinding.homeProgressBar.visibility = View.GONE
                    val weatherData = resultState.data
                    bindDataWithViews(weatherData)
                    initHourlyRecyclerView(weatherData)
                    initDailyRecyclerView(weatherData)
                }

                is ResultState.Error -> {
                    viewDataBinding.homeProgressBar.visibility = View.GONE
                    resultState.message?.let { showToastMsg(requireContext(), it) }
                    Log.e("Fragment", "Error: ${resultState.message}")
                }

                is ResultState.Loading -> {
                    viewDataBinding.homeProgressBar.visibility = View.VISIBLE
                    Log.d("Fragment", "Loading")
                }

                else -> {
                    viewDataBinding.homeProgressBar.visibility = View.GONE
                    Log.d("Fragment", "else")
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindDataWithViews(weatherData: WeatherDataEntity) {
        //LanguageUtils.setDefaultLanguage(preferenceManager,requireContext())
        viewDataBinding.city.text=weatherData.city
        viewDataBinding.temp.text=weatherData.temp
        viewDataBinding.weatherDesc.text=weatherData.description
        viewDataBinding.humidity.text=weatherData.humidity
        viewDataBinding.feelsLike.text=weatherData.feelsLike
        viewDataBinding.windSpeed.text=weatherData.windSpeed
        viewDataBinding.pressure.text=weatherData.pressure
        weatherData.iconCode?.let { weatherIconResourceId(it) }
            ?.let { viewDataBinding.icon.setImageResource(it) }



    }

    private fun initDailyRecyclerView(weatherData: WeatherDataEntity?) {
        dailyWeatherAdapter.submitList(weatherData?.dailyWeather)
        viewDataBinding.dailyRecyclerView.adapter = dailyWeatherAdapter
    }

    private fun initHourlyRecyclerView(weatherData: WeatherDataEntity?) {
        hourlyWeatherAdapter.submitList(weatherData?.hourlyWeather)
        viewDataBinding.recyclerView.adapter = hourlyWeatherAdapter
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
                    viewModel.getWeatherData(lat, lon)
                    preferenceManager.saveLatLng(LatLng(lat,lon))

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
}

