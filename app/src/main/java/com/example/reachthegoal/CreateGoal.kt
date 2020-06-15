package com.example.reachthegoal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.example.reachthegoal.RoomDatabase.GoalDao
import com.example.reachthegoal.RoomDatabase.GoalDatabase
import kotlinx.android.synthetic.main.activity_create_goal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateGoal  : AppCompatActivity(){

    lateinit var viewModel: GoalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)
        val start = intent.getStringExtra("date")
        val end = intent.getStringExtra("date2")
         ChooseBTstart.setOnClickListener{
             //W activity Calendar sprawdza przez który przycisk użytkownik wchodzi
             val startCheck = "start"
             val intent = Intent(this,Calendar::class.java)
             intent.putExtra("start",startCheck)
             intent.putExtra("name",CreateETname.text.toString())
             //Wysyła tekst do kalendarza aby  tekst na przycisku się nie zrestartował gdy wyjedziemy z aktywności
             intent.putExtra("textEndButton",end)
             startActivity(intent)
         }
        if(start != null) {
            ChooseBTstart.text = start
            val name2 = intent.getStringExtra("name")
                CreateETname.setText(name2)

        }
        ChooseBTend.setOnClickListener{
            val intent = Intent(this,Calendar::class.java)
            intent.putExtra("name",CreateETname.text.toString())
            //Wysyła tekst do kalendarza aby tekst na przycisku  się nie zrestartował gdy wyjedziemy z aktywności
            intent.putExtra("textStartButton",start)
            startActivity(intent)
        }
        if(end != null){
            ChooseBTend.text = end
            val name2 = intent.getStringExtra("name")
            CreateETname.setText(name2)

        }

       EnterBT.setOnClickListener{
           val goal = Goal(start,end,CreateETname.text.toString())
          viewModel = ViewModelProvider.AndroidViewModelFactory
              .getInstance(application).create(GoalViewModel::class.java)
           viewModel.insertGoal(goal)

           val intent = Intent(this,MainActivity::class.java)

           startActivity(intent)
       }

    }
}

