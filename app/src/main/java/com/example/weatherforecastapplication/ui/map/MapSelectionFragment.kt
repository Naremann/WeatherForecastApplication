package com.example.weatherforecastapplication.ui.map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentMapSelectionBinding
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.PreferenceManager
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import com.example.weatherforecastapplication.ui.ResultState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MapSelectionFragment : BaseFragment<FragmentMapSelectionBinding, MapSelectionViewModel>(),OnMapReadyCallback {
    @Inject
    lateinit var locationDao: LocationDao
    lateinit var navigator: Navigator
    @Inject
    lateinit var apiService: ApiService
    private val mapSelectionViewModel:MapSelectionViewModel by viewModels {
        MapSelectionVMFactory(
            WeatherRepo.WeatherRepoImp(
            WeatherRemoteSource.WeatherRemoteSourceImp(apiService),
            WeatherLocalSource.WeatherLocalSourceImp(locationDao)))
    }
    private lateinit var googleMap: GoogleMap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStateFlow()

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        viewDataBinding.selectLocationButton.setOnClickListener {
            if (::googleMap.isInitialized) {
                val selectedLocation = googleMap.cameraPosition.target
                navigateToHomeFragment(selectedLocation)
                (requireActivity() as? com.example.weatherforecastapplication.ui.home.Navigator)?.onLocationSelected(selectedLocation)
            } else {
                Log.e(TAG, "Google Map is not initialized")
            }
        }
        viewDataBinding.saveToFavBtn.setOnClickListener {
            if(::googleMap.isInitialized){
                val selectedLocation=googleMap.cameraPosition.target
                viewModel.saveLocationToFav(selectedLocation,requireContext(), PreferenceManager(requireContext()))
            }
        }
    }

    private fun observeStateFlow() {
        mapSelectionViewModel.favLocation.onEach { resultState ->
            when (resultState) {
                is ResultState.SuccessMsg -> {
                    viewDataBinding.saveToFavProgress.visibility = View.GONE
                    showToastMsg(requireContext(), "Location is added to favorite successfully")
                }

                is ResultState.Loading -> {
                    viewDataBinding.saveToFavProgress.visibility = View.VISIBLE
                }

                is ResultState.Error -> {
                    viewDataBinding.saveToFavProgress.visibility = View.GONE
                    showToastMsg(requireContext(), resultState.error)
                }

                else -> {
                    viewDataBinding.saveToFavProgress.visibility = View.GONE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): MapSelectionViewModel {
        return mapSelectionViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_map_selection
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

    private fun navigateToHomeFragment(latLng: LatLng) {
        val bundle = bundleOf("latLng" to latLng)
        view?.findNavController()?.navigate(R.id.action_mapSelectionFragment_to_homeFragment, bundle)

    }
    companion object {
        private const val TAG = "MapSelectionFragment"
    }

}