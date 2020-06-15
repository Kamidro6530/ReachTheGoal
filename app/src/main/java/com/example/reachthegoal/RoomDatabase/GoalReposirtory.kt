package com.example.reachthegoal.RoomDatabase

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

//Klasa decycudje jakie informacje przekazac dalej(np.W przypadku desynchronizacji)
class GoalReposirtory(application: Application) {
    private var goalDao : GoalDao
    init {
        val database = GoalDatabase.getInstance(application.applicationContext)
        goalDao = database!!.goalDao()
    }
    fun InsertGoal(goal :Goal) =
        CoroutineScope(Dispatchers.IO).launch {
            goalDao.insertGoal(goal)
        }
    fun getAll() : Deferred<LiveData<List<Goal>>> =
        CoroutineScope(Dispatchers.IO).async {
            goalDao.getAll() }
        fun deleteGoal(goal: Goal) =
            CoroutineScope(Dispatchers.IO).launch {
                goalDao.deleteGoal(goal)
            }


}