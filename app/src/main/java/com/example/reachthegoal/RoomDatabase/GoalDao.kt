package com.example.reachthegoal.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Deferred

@Dao
interface GoalDao {

    @Query("SELECT * FROM Goal ")
    fun getAll():LiveData<List<Goal>>
    @Insert
    fun insertGoal(vararg goal: Goal)
    @Delete
    fun deleteGoal(goal: Goal)
}