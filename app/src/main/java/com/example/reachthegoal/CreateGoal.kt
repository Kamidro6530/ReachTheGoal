package com.example.reachthegoal

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.example.reachthegoal.RoomDatabase.GoalDao
import com.example.reachthegoal.RoomDatabase.GoalDatabase
import kotlinx.android.synthetic.main.activity_create_goal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateGoal : AppCompatActivity() {

    lateinit var viewModel: GoalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)
        val start = intent.getStringExtra("date")
        val end = intent.getStringExtra("date2")
        ChooseBTstart.setOnClickListener {
            //W activity Calendar sprawdza przez który przycisk użytkownik wchodzi
            val startCheck = "start"
            val intent = Intent(this, Calendar::class.java)
            intent.putExtra("start", startCheck)
            intent.putExtra("name", CreateETname.text.toString())
            //Wysyła tekst do kalendarza aby  tekst na przycisku się nie zrestartował gdy wyjedziemy z aktywności
            intent.putExtra("textEndButton", end)
            startActivity(intent)
        }
        if (start != null) {
            ChooseBTstart.text = start
            val name2 = intent.getStringExtra("name")
            CreateETname.setText(name2)

        }
        ChooseBTend.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            intent.putExtra("name", CreateETname.text.toString())
            //Wysyła tekst do kalendarza aby tekst na przycisku  się nie zrestartował gdy wyjedziemy z aktywności
            intent.putExtra("textStartButton", start)
            startActivity(intent)
        }
        if (end != null) {
            ChooseBTend.text = end
            val name2 = intent.getStringExtra("name")
            CreateETname.setText(name2)

        }

        EnterBT.setOnClickListener {
            val splitStartDate = start.split("/")
            val splitEndDate = end.split("/")
            var d = days(splitStartDate, splitEndDate)
            var m = months(splitStartDate, splitEndDate)
            var y = years(splitStartDate, splitEndDate)
            /*Jezeli rok jest  poprawny*/ if (y >= 0) {

            /*Jezeli rok jest ten sam*/      if (y == 0) {
                /*Jezeli miesiac jest poprawny*/  if (m >= 0) {
                    if (m == 0) {
                        /*Jezeli dzien nie  jest poprawny*/     if (d < 0) {
                            inCorrect()
                        } else {
                            correct(start, end)
                        }
                    } else {
                        correct(start, end)
                    }
                } else {
                    inCorrect()
                }
            } else {
                correct(start, end)
            }

        } else {
            inCorrect()
        }




         }

        }
    fun inCorrect(){
        Toast.makeText(
            applicationContext,
            "Niepoprawna data zakończenia",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun correct(start : String?, end : String?  ){
        when {
            start == null -> {
                Toast.makeText(
                    applicationContext,
                    "Wybierz datę rozpoczęcia",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            end == null -> {
                Toast.makeText(
                    applicationContext,
                    "Wybierz datę zakończenia",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else -> {
                val goal = Goal(start, end, CreateETname.text.toString())
                viewModel = ViewModelProvider.AndroidViewModelFactory
                    .getInstance(application).create(GoalViewModel::class.java)
                viewModel.insertGoal(goal)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun days(splitStartDate : List<String>, splitEndDate: List<String>): Int {
        return splitEndDate[0].replace("\\s".toRegex(), "")
            .toInt() - splitStartDate[0].replace("\\s".toRegex(), "").toInt()
    }
    fun months(splitStartDate : List<String>, splitEndDate: List<String>) : Int {
        return splitEndDate[1].replace("\\s".toRegex(), "")
            .toInt() - splitStartDate[1].replace("\\s".toRegex(), "").toInt()
    }
    fun years(splitStartDate : List<String>, splitEndDate: List<String>) : Int {
        return splitEndDate[2].replace("\\s".toRegex(), "")
            .toInt() - splitStartDate[2].replace("\\s".toRegex(), "").toInt()
    }
}

