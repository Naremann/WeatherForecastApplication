/*
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), OnMapReadyCallback {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
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

      */
/*  viewDataBinding. .apply {
            adapter = hourlyAdapter
        }
        viewDataBinding.dailyRecyclerView.apply {
            adapter = dailyAdapter
        }*//*


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
            if (::googleMap.isInitialized) {
                addCurrentLocationMarker(lat.toString().toDouble(), lon.toString().toDouble())
            }
            if (lon != null && lat!=null) {
                viewModel1.getWeatherData(lat,lon)
            }
            viewDataBinding.viewModel=viewModel
            Log.e("TAG", "onLocationResult: ", )
           // viewDataBinding.recyclerView.adapter=hourlyAdapter

        }
    }

    private fun openMapFragment() {
        val existingFragment = supportFragmentManager.findFragmentById(R.id.mapContainer)
        if (existingFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit()
        } else {
            mapFragment = existingFragment as SupportMapFragment
        }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (latitudeTv.text.isNotEmpty() && longitudeTv.text.isNotEmpty()) {
            addCurrentLocationMarker(latitudeTv.text.toString().toDouble(), longitudeTv.text.toString().toDouble())
        }
        //addCurrentLocationMarker(latitudeTv.text.toString().toDouble(), longitudeTv.text.toString().toDouble())
    }

    private fun addCurrentLocationMarker(latitude: Double, longitude: Double) {
        */
/*  val currentLocation = LatLng(latitude, longitude)
          googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
          googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))*//*

        if (::googleMap.isInitialized) {
            val currentLocation = LatLng(latitude, longitude)
            googleMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
        } else {
            Toast.makeText(this, "Map is not ready yet", Toast.LENGTH_SHORT).show()
        }
    }

   */
/* override fun onResume() {
        super.onResume()
        getLocation()
    }*//*


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
*/
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
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.api.ApiService
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.db.PreferenceManager
import com.example.weatherforecastapplication.repo.WeatherRemoteSource
import com.example.weatherforecastapplication.repo.WeatherRepo
import com.example.weatherforecastapplication.ui.setting.SettingViewModel
import com.example.weatherforecastapplication.ui.setting.SettingViewModelFactory
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(){

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val My_LOCATION_PERMISSION_ID = 5005
    @Inject
    lateinit var apiService: ApiService

    private val viewModel1: HomeViewModel by viewModels {

        HomeViewModelFactory(WeatherRepo.WeatherRepoImp(WeatherRemoteSource.WeatherRemoteSourceImp(apiService)),PreferenceManager(requireContext()))
    }
    private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory(PreferenceManager(requireContext()))
    }


    private fun observeLocationMode() {
        settingViewModel.locationMode.observe(viewLifecycleOwner) { mode ->
            viewDataBinding.viewModel=viewModel1
            when (mode) {
                "GPS" -> getLocation()
                "Map" -> {
                    val latLng = arguments?.getParcelable<LatLng>("latLng")
                    latLng?.latitude?.let {
                        latLng.longitude.let { it1 ->
                            Log.e("TAG", "observeLocationMode: $it")
                            viewModel1.getWeatherData(it,
                                it1
                            )
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
        observeLocationMode()
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
                    viewModel1.getWeatherData(lat, lon)
                }
                viewDataBinding.viewModel=viewModel1

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
        return viewModel1
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MAP && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<LatLng>(EXTRA_SELECTED_LOCATION)?.let { selectedLocation ->
                // Handle the selected location here, for example, update UI or make API calls
                val lat = selectedLocation.latitude
                val lon = selectedLocation.longitude
                viewModel1.getWeatherData(lat, lon)
            }
        }
    }
    companion object {
        private const val REQUEST_CODE_MAP = 1001
        const val EXTRA_SELECTED_LOCATION = "selected_location"
    }


}


