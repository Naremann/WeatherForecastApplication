package com.example.weatherforecastapplication.ui.alerts

import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentWeaherAlertsBinding
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.repo.WeatherAlertRepoImp
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSourceImp
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherAlertsFragment : BaseFragment<FragmentWeaherAlertsBinding,WeatherAlertsViewModel>() {

    private var adapter = WeatherAlertAdapter()

    @Inject
    lateinit var weatherAlertDao: WeatherAlertDao


    private val alertViewModel:WeatherAlertsViewModel by viewModels {
        AlertsViewModelFactory(
            WeatherAlertRepoImp(
                WeatherAlertLocalSourceImp(weatherAlertDao)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        lifecycleScope.launch {
            viewModel.weatherAlertsViewState.collectLatest { state ->
                when (state) {
                    is WeatherAlertsViewState.Loading -> showLoading()
                    is WeatherAlertsViewState.Success -> showWeatherAlerts(state.weatherAlerts)
                    is WeatherAlertsViewState.Error -> showToastMsg(requireContext(), state.message)
                }
            }
        }

        viewDataBinding.rvWeatherAlerts.adapter = adapter

        viewDataBinding.btnAddWeatherAlert.setOnClickListener {
            val addDialog = AddWeatherAlertDialogFragment()
            addDialog.show(parentFragmentManager, "AddWeatherAlert")
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "weather_alerts"
            val channelName = "Weather Alerts"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Channel for Weather Alerts"
            }

            val notificationManager = requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showLoading() {
        // Show loading state in UI
    }

    private fun showWeatherAlerts(weatherAlerts: List<WeatherAlertEntity>) {
        weatherAlerts.forEach {
            Log.e("TAG", "showWeatherAlerts: "+it.alarmType)
        }
        adapter.submitList(weatherAlerts)
    }

    override fun getFragmentView(): View? {
        return viewDataBinding.root
    }

    override fun initViewModel(): WeatherAlertsViewModel {
        return alertViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_weaher_alerts
    }

}