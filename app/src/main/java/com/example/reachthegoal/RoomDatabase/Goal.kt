package com.example.reachthegoal.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity

data class Goal(

    val start: String,
    val end: String,
    val name: String,
    val numberOfBreaks : Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}