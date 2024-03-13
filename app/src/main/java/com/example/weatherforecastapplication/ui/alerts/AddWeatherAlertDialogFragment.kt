package com.example.weatherforecastapplication.ui.alerts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.db.WeatherAlertDao
import com.example.weatherforecastapplication.data.model.WeatherAlertEntity
import com.example.weatherforecastapplication.data.repo.WeatherAlertRepo
import com.example.weatherforecastapplication.data.repo.WeatherAlertRepoImp
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.repo.local.WeatherAlertLocalSourceImp
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import com.example.weatherforecastapplication.databinding.FragmentAddWeatherAlertDialogBinding
import com.example.weatherforecastapplication.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddWeatherAlertDialogFragment : DialogFragment() {

    @Inject
    lateinit var weatherAlertDao: WeatherAlertDao

    private val viewModel: WeatherAlertsViewModel by viewModels {
        AlertsViewModelFactory(
            WeatherAlertRepoImp(
                WeatherAlertLocalSourceImp(weatherAlertDao)
            )
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddWeatherAlertDialogBinding>(
            inflater,
            R.layout.fragment_add_weather_alert_dialog,
            container,
            false
        )

        binding.btnAddAlert.setOnClickListener {
            val alarmType = if (binding.radioAlarm.isChecked) {
                "Alarm"
            } else {
                if (!isNotificationServiceEnabled()) {
                    openNotificationAccessSettings()
                }
                "Notification"
            }

            val hour = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                binding.timePickerDuration.hour
            } else {
                binding.timePickerDuration.currentHour
            }
            val minute = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                binding.timePickerDuration.minute
            } else {
                binding.timePickerDuration.currentMinute
            }
            val durationMinutes = getDurationInMinutes(hour, minute)
            val isActive = binding.switchAlertEnabled.isChecked

            val weatherAlert = WeatherAlertEntity(
                hour = hour,
                minute = minute,
                duration = durationMinutes,
                alarmType = alarmType,
                isActive = isActive
            )

            viewModel.addWeatherAlert(weatherAlert, requireContext())

            Toast.makeText(requireContext(), "Weather alert added", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return binding.root
    }
    private fun isNotificationServiceEnabled(): Boolean {
        val packageName = requireActivity().packageName
        val flat = Settings.Secure.getString(requireActivity().contentResolver, "enabled_notification_listeners")
        return flat != null && flat.contains(packageName)
    }


    private fun openNotificationAccessSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
        startActivity(intent)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.REQUEST_CODE_NOTIFICATION_ACCESS) {
            if (resultCode == Activity.RESULT_OK) {
            } else {
            }
        }
    }
    private fun getDurationInMinutes(hour: Int, minute: Int): Long {
        return (hour * 60 + minute).toLong()
    }


}


