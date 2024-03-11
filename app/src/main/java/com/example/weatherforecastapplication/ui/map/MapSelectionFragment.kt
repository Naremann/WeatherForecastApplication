package com.example.weatherforecastapplication.ui.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.FragmentMapSelectionBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapSelectionFragment : Fragment(),OnMapReadyCallback {
    private var _binding: FragmentMapSelectionBinding? = null
    private val binding get() = _binding!!
    lateinit var navigator: Navigator

    private lateinit var googleMap: GoogleMap
    private var locationListener: com.example.weatherforecastapplication.ui.home.Navigator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        binding.selectLocationButton.setOnClickListener {
            if (::googleMap.isInitialized) {
                val selectedLocation = googleMap.cameraPosition.target
               /* val action = MapSelectionFragmentDirections.actionMapSelectionFragmentToHomeFragment(
                    LatLng(selectedLocation.latitude, selectedLocation.longitude)
                )
                findNavController().navigate(action)*/
                navigateToHomeFragment(selectedLocation)

                (requireActivity() as? com.example.weatherforecastapplication.ui.home.Navigator)?.onLocationSelected(selectedLocation)
            } else {
                Log.e(TAG, "Google Map is not initialized")
            }
        }


    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val defaultLocation = LatLng(31.4543408, 30.1597019)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng))
           // navigator.navigateToHomeFragment(latLng)

        }
    }

    private fun navigateToHomeFragment(latLng: LatLng) {
        val bundle = bundleOf("latLng" to latLng)
        view?.findNavController()?.navigate(R.id.action_mapSelectionFragment_to_homeFragment, bundle)
        //val action=MapSelectionFragmentDirections.actionMapSelectionFragmentToHomeFragment(latLng)
        /*val navHostFragment=requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController=navHostFragment.navController
        navController.navigate(action)*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
   /* private fun onLocationSelected(latLng: LatLng) {
        mapSelectionListener?.onLocationSelected(latLng)
        parentFragmentManager.popBackStack()
    }*/


    fun setLocationListener(listener: com.example.weatherforecastapplication.ui.home.Navigator) {
        this.locationListener = listener
    }

    companion object {
        private const val TAG = "MapSelectionFragment"
    }

}


/*
    private var _binding: FragmentMapSelectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


        binding.selectLocationButton.setOnClickListener {
            if (::googleMap.isInitialized) {
                val selectedLocation = googleMap.cameraPosition.target
                val action = MapSelectionFragmentDirections.actionMapSelectionFragmentToHomeFragment(
                    LatLng(selectedLocation.latitude, selectedLocation.longitude)
                )
                findNavController().navigate(action)
            } else {
                Log.e(TAG, "Google Map is not initialized")
            }
        }
    }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val defaultLocation = LatLng(31.4543408, 30.1597019)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

        googleMap.setOnMapClickListener { latLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "MapSelectionFragment"
    }*/


