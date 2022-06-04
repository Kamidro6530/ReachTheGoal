package com.example.reachthegoal

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.checklist_activity.*
import kotlinx.android.synthetic.main.checklist_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar
import java.util.concurrent.TimeUnit
import java.util.zip.Inflater

lateinit var list : LiveData<List<Goal>>
lateinit var viewModel: GoalViewModel
lateinit var goalAdapter: CheckRecyclerAdapter
var today : Calendar = Calendar.getInstance()
var timer : Timer = Timer()
var generator: Random = Random()

class CheckListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checklist_activity)

        class RecyclerListReset() : TimerTask() {
            override fun run() {

                Handler(Looper.getMainLooper()).post{
                    Toast.makeText(applicationContext,"Good", Toast.LENGTH_SHORT).show() }

            }

        }
        //Ustawienie godziny wykonania resetu listy
        today.set(Calendar.HOUR_OF_DAY,0)
        today.set(Calendar.MINUTE,0)
        today.set(Calendar.SECOND,0)

        timer.schedule(RecyclerListReset(), today.time,TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS))


        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(GoalViewModel::class.java)

        list = viewModel.getAllGoals()
        list.observe(this, Observer {
            if (it.isNotEmpty()) {
                goalAdapter = CheckRecyclerAdapter(it)
                checkRecycler.adapter = goalAdapter

            }
        })


        checkRecycler.layoutManager = LinearLayoutManager(this)
        checkRecycler.setHasFixedSize(true)
         CoroutineScope(Dispatchers.IO).launch {
             
         }

                //Przycisk dodawania wyzwania w Pasku Bocznym
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_button -> {
                    val intent = Intent(this, CreateGoal::class.java)
                    intent.putExtra("list",list.toString())
                    startActivity(intent)

                }
                R.id.send_notification ->{

                    val intent = Intent(this, MainActivity::class.java)

                    val pendingIntent= PendingIntent.getActivity(this, 1,intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val notification: NotificationCompat.Builder =
                        NotificationCompat.Builder(this, AlarmReceiverActivity.CHANNEL_ID)
                            .setContentTitle("Czy wykonałeś dzisiejszą część wyzwania ?!")
                            .setContentText("")
                            .setSmallIcon(R.drawable.ic_baseline_alarm_on_24)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val sercviceChannel = android.app.NotificationChannel(
                            NotificationChannel.CHANNEL_ID.toString(), "ServiceChannel",
                            NotificationManager.IMPORTANCE_DEFAULT
                        )

                        val manager =
                            ContextCompat.getSystemService(this, NotificationManager::class.java)
                        manager?.createNotificationChannel(sercviceChannel)
                        manager?.notify(generator.nextInt(), notification.build())


                    }
                }
                R.id.notifications_button ->{
                    val intent = Intent(this,NotificationActivity::class.java)

                    startActivity(intent)
                }
                R.id.list_check -> {
                    val intent = Intent(this,CheckListActivity::class.java)
                    startActivity(intent)
                }
                R.id.goal_list -> {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }

            }

            true
        }
    }
}