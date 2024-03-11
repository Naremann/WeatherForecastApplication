package com.example.weatherforecastapplication.ui.map

import com.google.android.gms.maps.model.LatLng

interface Navigator {
    fun navigateToHomeFragment(latLng: LatLng)
}