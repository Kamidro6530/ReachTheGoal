package com.example.reachthegoal.GoalViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import com.example.reachthegoal.RoomDatabase.GoalReposirtory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

class GoalViewModel(application: Application) : AndroidViewModel(application) {
    private var goalRepository : GoalReposirtory = GoalReposirtory(application)

    private  var allGoals : Deferred<LiveData<List<Goal>>> = goalRepository.getAll()

    fun insertGoal(goal : Goal ){
        goalRepository.InsertGoal(goal)
    }
    fun deleteGoal(goal: Goal){
        goalRepository.deleteGoal(goal)
    }
    fun getAllGoals(): LiveData<List<Goal>> = runBlocking{
        allGoals.await()
    }
}