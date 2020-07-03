package com.example.reachthegoal

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.cardview_item.*
import kotlinx.android.synthetic.main.cardview_item.view.*
import kotlinx.android.synthetic.main.cardview_item.view.End_TV
import kotlinx.android.synthetic.main.cardview_item.view.Start_TV
import kotlinx.android.synthetic.main.goalactivity.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.coroutines.coroutineContext

class RecyclerAdapter(private val list: List<Goal>) : RecyclerView.Adapter<MyHolder>() {
    lateinit var viewModel: GoalViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cardview_item, parent, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(Application()).create(GoalViewModel::class.java)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val delete: ImageButton = holder.itemView.Icon_BT
        holder.Name.text = list[position].name
        holder.Start.text = "Start : " + list[position].start
        holder.End.text = "End : " + list[position].end



        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, GoalActivity::class.java)
            intent.putExtra("Name", list[position].name)
            intent.putExtra("Start", list[position].start)
            intent.putExtra("End", list[position].end)
            startActivity(holder.itemView.context, intent, null)


        }

        delete.setOnClickListener {
            viewModel.deleteGoal(list[position])
            holder.itemView.visibility = View.GONE
        }
      //var start_TV = holder.Start.text.toString()
      //var end_TV = holder.End.text.toString()
        var start_TV = holder.Start.text.split(":")
        var end_TV = holder.End.text.split(":")

        val splitStartDate = start_TV[1].split(" / ")
        val splitEndDate = end_TV[1].split(" / ")
        var splitNowDate = LocalDate.now().toString().split("-")

        var dtf = SimpleDateFormat("dd/MM/yyyy")

        var currentdate = dtf.parse(
            splitNowDate[2] + "/" + splitNowDate[1] + "/" + splitNowDate[0].replace(
                "\\s".toRegex(),
                ""
            )
        )
        var enddate = dtf.parse(
            splitEndDate[0] + "/" + splitEndDate[1] + "/" + splitEndDate[2].replace(
                "\\s".toRegex(),
                ""
            )
        )
        var startdate = dtf.parse(
            splitStartDate[0] + "/" + splitStartDate[1] + "/" + splitStartDate[2].replace(
                "\\s".toRegex(),
                ""
            )
        )
        //Oblicza ile czasu zostało do końca wyzwania od obecnego dnia
        var daysBetween = enddate.time - currentdate.time
        //Długość całego wyzwania
        var durationInDays = enddate.time - startdate.time
        var progressBar = holder.itemView.progressBarItem
        progressBar.max = 100
        progressBar.rotation = 180.toFloat()
        progressBar.progressTintList = ColorStateList.valueOf(Color.GRAY)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(Color.rgb(98, 0, 238))
        progressBar.progress =percentage(daysBetween.toDouble(), durationInDays.toDouble()).toInt()


    }
    fun percentage(first: Double, second: Double): Double {
        return first * 100 / second
    }
    }




class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
    val Name = itemView.Title_TV
    val Start = itemView.Start_TV
    val End = itemView.End_TV

}


