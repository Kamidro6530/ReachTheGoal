package com.example.reachthegoal

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cardview_item.*
import kotlinx.android.synthetic.main.goalactivity.*
import java.text.SimpleDateFormat
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    lateinit var list : LiveData<List<Goal>>
    lateinit var viewModel: GoalViewModel
    lateinit var goalAdapter: RecyclerAdapter

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
            }
            true
        }


}}
