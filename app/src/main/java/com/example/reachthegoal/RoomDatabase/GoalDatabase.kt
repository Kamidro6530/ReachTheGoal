package com.example.reachthegoal.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Goal::class), version = 5)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao


    companion object {
        var instance: GoalDatabase? = null

        fun getInstance(context: Context): GoalDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    GoalDatabase::class.java,
                    "goal_table"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
