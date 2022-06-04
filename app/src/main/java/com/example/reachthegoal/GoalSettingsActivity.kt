package com.example.reachthegoal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.goal_settings_activity.*

class GoalSettingsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_settings_activity)
        val firstNotificationTime = intent.getStringExtra("firstNotification")
        val numberOfNotification = intent.getStringExtra("numberOfNotification")

        numberTV.text = "Liczba przypomień : $numberOfNotification"

        firstNotificationTV.text = "Godzina wysłania pierwszego przypomnienia : $firstNotificationTime"
    }
}