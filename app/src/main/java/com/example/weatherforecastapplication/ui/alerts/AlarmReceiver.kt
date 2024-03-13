package com.example.weatherforecastapplication.ui.alerts

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.weatherforecastapplication.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_DELETE -> {
                Log.d(TAG, "Notification dismissed")
                stopAlarmSound(context)
            }
            else -> {
                val alertType = intent.getStringExtra(ALERT_TYPE_EXTRA)
                val notification = buildNotification(alertType ?: "Notification", context)
                showNotification(context, notification)
                if (alertType == "Alarm") {
                    playAlarmSound(context)
                }
            }
        }
    }

    private fun buildNotification(alertType: String, context: Context): NotificationCompat.Builder {
        val channelId = "weather_alerts"

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle("Weather Alert")
            .setContentText("Weather alert message here")
            .setAutoCancel(true)

        val soundUri = when (alertType) {
            "Alarm" -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            else -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        notificationBuilder.setSound(soundUri)

        return notificationBuilder
    }

    private fun showNotification(context: Context, notificationBuilder: NotificationCompat.Builder) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val notification = notificationBuilder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun playAlarmSound(context: Context) {
        val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, alarmSoundUri)
        ringtone.play()
    }

    private fun stopAlarmSound(context: Context) {
        val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, alarmSoundUri)
        ringtone.stop()
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 1234
        const val ALERT_TYPE_EXTRA = "alert_type"
        fun stopAlarmSound(context: Context) {
            val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val ringtone = RingtoneManager.getRingtone(context, alarmSoundUri)
            ringtone.stop()
        }
    }
}
