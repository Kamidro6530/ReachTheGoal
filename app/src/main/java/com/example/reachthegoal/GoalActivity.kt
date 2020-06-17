package com.example.reachthegoal

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.goalactivity.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.format.FormatStyle
import java.util.*

class GoalActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.reachthegoal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goalactivity)


        val Name = intent.getStringExtra("Name")
        val Start = intent.getStringExtra("Start")
        val End = intent.getStringExtra("End")

        NameTV.text = Name
        StartTV.text = "start date of the challenge $Start"
        EndTV.text = "end date of the challenge $End"




     //Podzielenie stringa do obliczania dni do końca wyzwania
     //val splitStartDate = Start.split("/")
    // val splitEndDate = End.split("/")
     //val sdf = SimpleDateFormat("dd/MM/yyyy")
     // val startDate = sdf.parse(""+splitStartDate[2]+splitStartDate[1]+splitStartDate[0]+"").toString()
     // val endDate = sdf.parse(""+splitEndDate[2]+splitEndDate[1]+splitEndDate[0]+"").toString()

    // if(splitStartDate[1]==splitEndDate[1]){
    //     val result = splitEndDate[0].toInt()-splitStartDate[0].toInt()
     //    daysBetweenTV.text = result.toString()
     //}else{
           //Obliczanie różnicy dni między datami
    //  val days = endDate.toInt() - startDate.toInt()
    //   daysBetweenTV.text = days.toString()
      }
//
       // val currentDate = LocalDate.now()
      //  val computerDay = currentDate.format(DateTimeFormatter.ofPattern("dd"))

        // val days2 = ChronoUnit.DAYS.between(currentDate,splitEndDate[0])
       // Button.setOnClickListener{


        }



           // }

