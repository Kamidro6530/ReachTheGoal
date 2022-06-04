package com.example.reachthegoal

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.reachthegoal.GoalViewModel.GoalViewModel
import com.example.reachthegoal.RoomDatabase.Goal
import kotlinx.android.synthetic.main.checklist_item.view.*
import java.util.*
import kotlin.coroutines.coroutineContext

// Adapter RecyclerView do MainActivity
class CheckRecyclerAdapter(private val list: List<Goal>) : RecyclerView.Adapter<CheckMyHolder>() {
    lateinit var viewModel: GoalViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckMyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.checklist_item, parent, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(Application()).create(GoalViewModel::class.java)


        return CheckMyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CheckMyHolder, position: Int) {

        if (list.isEmpty()){
            viewModel.insertGoal(Goal("0","0","0",0))
        }

    holder.Name.text = list[position].name
    var breakGoal = list[position].numberOfBreaks
    holder.yesBT.setOnClickListener {



    }

    holder.noBT.setOnClickListener {
        viewModel.updateGoal(Goal(list[position].start,list[position].end,list[position].name,breakGoal++))
        holder.itemView.visibility = View.GONE
        val intent = Intent(holder.itemView.context,BrakeActivity::class.java)
      // startActivity(holder.itemView.context,intent,null)
    }

    }

}
    class CheckMyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val Name = itemView.checkItemTV
        val yesBT = itemView.yes_button
        val noBT = itemView.no_button
    }


