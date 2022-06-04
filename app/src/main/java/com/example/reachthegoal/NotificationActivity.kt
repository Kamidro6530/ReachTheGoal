package com.example.reachthegoal

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notification.*
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


        var number = 3
        NotificationNumberTV.text = number.toString()
        ImageUpBT.setOnClickListener {
            number++
            NotificationNumberTV.text = number.toString()
            if( number > 5 ){
                number = 5
                NotificationNumberTV.text = number.toString()
            }
        }
        ImageDownBT.setOnClickListener {
            number--
            NotificationNumberTV.text = number.toString()
            if( number < 0 ){
                number = 0
                NotificationNumberTV.text = number.toString()
            }
        }
        FirstdatePicker.setIs24HourView(true)




        NotificationEnterBT.setOnClickListener {
            //Funkcja odpowiedzialna za ustawianie godzin wysyłania powiadomień
            fun notification(date : Calendar){
                //Intent który przenosi nazwę wyzwania do Powiadomienia
                var notificationNameIntent = Intent(applicationContext,AlarmReceiverActivity::class.java)

                val pendingIntent = Intent(this, BrakeActivity::class.java)
                pendingIntent.putExtra(Notification.EXTRA_NOTIFICATION_ID, 0)

                var alarmManager  = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                var alarmIntent  = PendingIntent.getBroadcast(applicationContext
                    , 1
                    ,notificationNameIntent,0)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,date.timeInMillis, AlarmManager.INTERVAL_DAY,alarmIntent)
            }
            //Funkcja ustalająca czas wysłania powiadomienia
            fun timeofDay(hour : Int) : Calendar {
                return Calendar.Builder().setTimeOfDay(hour,0,0).build()
            }
            var hour = FirstdatePicker.hour

            //Sprawdza ile użytkwnik przypisał powiadomień i wyświetla je w odstępie 2 godzin
            when {
                (NotificationNumberTV.text as String).toInt() == 1 -> {
                    var day = timeofDay(hour)
                    notification(day)
                }
                (NotificationNumberTV.text as String).toInt() == 2 -> {
                    var day = timeofDay(hour)
                    notification(day)
                    var day2 = timeofDay(hour+2)
                    notification(day2)
                }
                (NotificationNumberTV.text as String).toInt() == 3 -> {
                    var day = timeofDay(hour)
                    notification(day)
                    var day2 = timeofDay(hour+2)
                    notification(day2)
                    var day3 = timeofDay(hour+4)
                    notification(day3)
                }
                (NotificationNumberTV.text as String).toInt() == 4 -> {
                    var day = timeofDay(hour)
                    notification(day)
                    var day2 = timeofDay(hour+2)
                    notification(day2)
                    var day3 = timeofDay(hour+4)
                    notification(day3)
                    var day4 = timeofDay(hour+6)
                    notification(day4)
                }
                (NotificationNumberTV.text as String).toInt() == 5 -> {
                    var day = timeofDay(hour)
                    notification(day)
                    var day2 = timeofDay(hour+2)
                    notification(day2)
                    var day3 = timeofDay(hour+4)
                    notification(day3)
                    var day4 = timeofDay(hour+6)
                    notification(day4)
                    var day5 = timeofDay(hour+8)
                    notification(day5)
                }
            }
            var intent = Intent(this, MainActivity::class.java)

            startActivity(intent)



        }
    }


}

