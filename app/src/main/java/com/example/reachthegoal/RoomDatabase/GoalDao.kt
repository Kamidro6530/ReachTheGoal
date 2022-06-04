package com.example.reachthegoal.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GoalDao {

    @Query("SELECT * FROM Goal ")
    fun getAll(): LiveData<List<Goal>>

    @Insert
    fun insertGoal(vararg goal: Goal)

    @Delete
    fun deleteGoal(goal: Goal)

    @Update
    fun updateGoal(goal: Goal)
}