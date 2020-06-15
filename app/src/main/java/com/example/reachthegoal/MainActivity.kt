package com.example.reachthegoal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.cardview_item.*

class MainActivity : AppCompatActivity() {
    lateinit var list : LiveData<List<Goal>>
    lateinit var viewModel : GoalViewModel
    lateinit var goalAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(application).create(GoalViewModel::class.java)



        list = viewModel.getAllGoals()
        list.observe(this, Observer {
            if(it.isNotEmpty()){
                goalAdapter = RecyclerAdapter(it)
                recycler.adapter = goalAdapter

            }
        })
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)


        //Przycisk dodawania wyzwania w Pasku Bocznym
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener{ menuItem ->
            when(menuItem.itemId) {
                R.id.add_button -> {
                    val intent = Intent(this, CreateGoal::class.java)
                    startActivity(intent)

                }
            }
            true
        }




    }





}
