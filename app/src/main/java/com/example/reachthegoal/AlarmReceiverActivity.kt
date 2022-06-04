package com.example.reachthegoal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.reachthegoal.RoomDatabase.Goal
import java.util.*


class AlarmReceiverActivity : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "second_ID"
        const val chanelName = "CHANNEL_NAME"
    }

    var generator = Random()

    override fun onReceive(context: Context, intent: Intent) {



        val intent = Intent(context, MainActivity::class.java)

        val pendingIntent=PendingIntent.getActivity(context, 1,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val notification: NotificationCompat.Builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Czy wykonałeś dzisiejszą część wyzwania  ?!")
                .setContentText(" Naciśnij na powiadomienie ")
                .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sercviceChannel =NotificationChannel(
                CHANNEL_ID, "ServiceChannel",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager =
                getSystemService(context, NotificationManager::class.java)
            manager?.createNotificationChannel(sercviceChannel)
            manager?.notify(generator.nextInt(), notification.build())
        }

    }


}