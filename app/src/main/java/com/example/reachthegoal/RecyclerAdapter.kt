package com.example.reachthegoal

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.cardview_item.view.*
import java.lang.Integer.parseInt
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

    }


}

class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
    val Name = itemView.Title_TV
    val Start = itemView.Start_TV
    val End = itemView.End_TV

}


