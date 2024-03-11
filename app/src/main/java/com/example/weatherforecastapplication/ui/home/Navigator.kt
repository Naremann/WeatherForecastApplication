package com.example.weatherforecastapplication.ui.home

import com.google.android.gms.maps.model.LatLng

interface Navigator {
    fun onLocationSelected(latLng: LatLng)

}