package com.example.reachthegoal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_breake.*

class BrakeActivity : AppCompatActivity() {

        var breakDays = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breake)
        breakDays++
        breakTV.text = "Dzień przerwy w wyzwaniu to nic złego ale jeżeli ilość dni przerwy przekroczy 10 % dni całego wyzwania to twój cel nie zostanie zaliczony"

        days_break.text = "Ilość powiadomień w których użytkownik kliknął 'przerwa' = $breakDays / 10 % z maksymalnej liczb dni wyzwania"

        breakBT.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}