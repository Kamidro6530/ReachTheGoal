package com.example.reachthegoal


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reachthegoal.AlarmReceiverActivity.Companion.CHANNEL_ID
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var list : LiveData<List<Goal>>
    lateinit var viewModel: GoalViewModel
    lateinit var goalAdapter: RecyclerAdapter

    var generator: Random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(GoalViewModel::class.java)



        list = viewModel.getAllGoals()
        list.observe(this, Observer {
            if (it.isNotEmpty()) {
               goalAdapter = RecyclerAdapter(it)
               recycler.adapter = goalAdapter

            }
        })
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)





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

                    val pendingIntent=PendingIntent.getActivity(this, 1,intent,PendingIntent.FLAG_UPDATE_CURRENT)

                    val notification: NotificationCompat.Builder =
                        NotificationCompat.Builder(this, CHANNEL_ID)
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
