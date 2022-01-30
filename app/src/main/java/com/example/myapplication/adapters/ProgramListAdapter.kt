package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ProgramHasExercise
import com.example.myapplication.R

class ProgramListAdapter : RecyclerView.Adapter<ProgramListAdapter.ViewHolder>() {

    private var listOfPrograms = emptyList<ProgramHasExercise>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.programItemTitleTV)
        var days: TextView = itemView.findViewById(R.id.programItemDaysTV)
        var weeks: TextView = itemView.findViewById(R.id.programItemWeeksTV)
        var progressBar: ProgressBar = itemView.findViewById(R.id.programItemProgressBar)
        //Реализовать функционал просмотра справки о программе тренировок
        var learnButton: Button = itemView.findViewById(R.id.programItemLearnMoreBtn)
        //Релизовать функционал выбора программы
        var selectButton: Button = itemView.findViewById(R.id.programItemSelectBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.program_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listOfPrograms[position].program.program_title
        holder.days.text = holder.itemView.context.getString(
            R.string.plans_days,
            listOfPrograms[position].program.days
        )
        holder.weeks.text = holder.itemView.context.getString(
            R.string.plans_weeks,
            listOfPrograms[position].program.weeks
        )
        if (listOfPrograms[position].program.difficulty != null) {
            holder.progressBar.progress = listOfPrograms[position].program.difficulty?.times(33)!!
        }
    }

    override fun getItemCount(): Int {
        return listOfPrograms.size
    }

    fun setData(list: List<ProgramHasExercise>) {
        listOfPrograms = list
        notifyDataSetChanged()
    }

}