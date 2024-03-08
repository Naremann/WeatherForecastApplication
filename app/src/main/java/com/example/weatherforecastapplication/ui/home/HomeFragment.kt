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
import androidx.fragment.app.viewModels
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.api.ApiService
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.repo.WeatherRemoteSource
import com.example.weatherforecastapplication.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val My_LOCATION_PERMISSION_ID = 5005
    @Inject lateinit var apiService:ApiService
    private val hourlyAdapter = HourlyWeatherAdapter()
    private val viewModel1: HomeViewModel by viewModels {
        ViewModelFactory(WeatherRepo.WeatherRepoImp(WeatherRemoteSource.WeatherRemoteSourceImp(apiService)))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  initHourlyRecyclerView()
     //   initDailyRecyclerView()

      /*  viewDataBinding. .apply {
            adapter = hourlyAdapter
        }
        viewDataBinding.dailyRecyclerView.apply {
            adapter = dailyAdapter
        }*/

        getLocation()

    }

    private fun initDailyRecyclerView() {

    }

    private fun initHourlyRecyclerView() {
        val recyclerView = viewDataBinding.recyclerView
        recyclerView.adapter = hourlyAdapter
    }


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            val lon= lastLocation?.longitude
            val lat= lastLocation?.latitude
            if (lon != null && lat!=null) {
                viewModel1.getWeatherData(lat,lon)
            }
            viewDataBinding.viewModel=viewModel
            Log.e("TAG", "onLocationResult: ", )
           // viewDataBinding.recyclerView.adapter=hourlyAdapter

        }
    }

   /* override fun onResume() {
        super.onResume()
        getLocation()
    }*/

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
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
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

    @SuppressLint("MissingPermission")
    private fun requestNewLocation() {
        val locationResult = LocationRequest()
        locationResult.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationResult.setInterval(0)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.requestLocationUpdates(
            locationResult,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }






    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): HomeViewModel {
        return viewModel1

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }
}
