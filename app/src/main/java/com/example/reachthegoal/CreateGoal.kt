package com.example.reachthegoal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import kotlinx.android.synthetic.main.activity_create_goal.*

class CreateGoal : AppCompatActivity() {

    lateinit var viewModel: GoalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_goal)
        var start = intent.getStringExtra("date")
        var end = intent.getStringExtra("date2")
        var name = intent.getStringExtra("name")


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
            if (start == null || end == null ) {
                Toast.makeText(
                    applicationContext,
                    "Musisz wybrać daty rozpoczęcia i zakończenia wyzwania oraz ilość i czas powiadomień",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
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




    }

    fun inCorrect() {
        Toast.makeText(
            applicationContext,
            "Niepoprawna data zakończenia",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun correct(start: String?, end: String?) {

        val goal = Goal(start.toString(), end.toString(), CreateETname.text.toString(),0)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(GoalViewModel::class.java)
        viewModel.insertGoal(goal)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}

private fun days(splitStartDate: List<String>, splitEndDate: List<String>): Int {
    return splitEndDate[0].replace("\\s".toRegex(), "")
        .toInt() - splitStartDate[0].replace("\\s".toRegex(), "").toInt()
}

fun months(splitStartDate: List<String>, splitEndDate: List<String>): Int {
    return splitEndDate[1].replace("\\s".toRegex(), "")
        .toInt() - splitStartDate[1].replace("\\s".toRegex(), "").toInt()
}

fun years(splitStartDate: List<String>, splitEndDate: List<String>): Int {
    return splitEndDate[2].replace("\\s".toRegex(), "")
        .toInt() - splitStartDate[2].replace("\\s".toRegex(), "").toInt()
}


