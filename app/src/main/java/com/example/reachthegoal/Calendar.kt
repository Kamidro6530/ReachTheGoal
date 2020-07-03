package com.example.reachthegoal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.calendar_view.*

class Calendar : AppCompatActivity() {
    private var FromStart = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_view)
        //Pobieranie dat z przycisku
        val textStartButton = intent.getStringExtra("textStartButton")
        val textEndButton = intent.getStringExtra("textEndButton")
        val name = intent.getStringExtra("name")

        val whichButton = "start"
        //Sprawdza czy użytkownik przyszedł do aktywności z przycisku start
        if (intent.getStringExtra("start") == whichButton) {
            FromStart = true
        }
        val mustSelect = "Wybrana data"
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //Zwiększa liczbe miesiąca o 1 ponieważ kalendarz liczy miesiące od 0 do 11
            val month1up = month + 1
            //Dodanie 0 przed jednocyfrowym miesiącem
            var mo = String.format("%02d",month1up)
            DateTV.text = "$dayOfMonth / $mo / $year"

        }
        EnterDateBT.setOnClickListener {
            //Blokuje możliwość przejścia dalej bez wybrania daty
            if (DateTV.text.toString() != mustSelect) {
                val intent = Intent(this, CreateGoal::class.java)
                if (FromStart == true) {
                    intent.putExtra("date", DateTV.text.toString())
                    intent.putExtra("name", name)
                    //Wysyłanie tekstu z przycisku zpowrotem do aktywności
                    intent.putExtra("date2", textEndButton)
                } else {
                    intent.putExtra("name", name)
                    intent.putExtra("date2", DateTV.text.toString())
                    //Wysyłanie tekstu z przycisku zpowrotem do aktywności
                    intent.putExtra("date", textStartButton)
                }

                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Musisz wybrać datę", Toast.LENGTH_LONG).show()
            }
        }
    }









}