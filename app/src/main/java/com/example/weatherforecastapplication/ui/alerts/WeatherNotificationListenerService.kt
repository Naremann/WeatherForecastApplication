package com.example.weatherforecastapplication.ui.alerts

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class WeatherNotificationListenerService : NotificationListenerService() {

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        Log.d(TAG, "onNotificationRemoved: Notification removed")
        AlarmReceiver.stopAlarmSound(this)
    }

    companion object {
        private const val TAG = "NotificationListener"
    }
}

