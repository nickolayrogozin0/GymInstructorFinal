package com.brxq.gyminstructor.ui.programs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.model.Program

class ProgramListAdapter(
    private val onProgramClick: OnProgramClick
) : RecyclerView.Adapter<ProgramListAdapter.ViewHolder>() {

    val listOfPrograms = mutableListOf<Program>()

    interface OnProgramClick{
        fun selectProgram(adapterPosition : Int)
        fun learnMore(adapterPosition: Int)
    }

    class ViewHolder(
        itemView: View,
        onProgramClick: OnProgramClick
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.programItemTitleTV)
        val days: TextView = itemView.findViewById(R.id.programItemDaysTV)
        val weeks: TextView = itemView.findViewById(R.id.programItemWeeksTV)
        val progressBar: ProgressBar = itemView.findViewById(R.id.programItemProgressBar)
        val selectProgramBtn : Button = itemView.findViewById(R.id.programItemSelectBtn)
        val learnMoreBtn : Button = itemView.findViewById(R.id.programItemLearnMoreBtn)

        init {

            selectProgramBtn.setOnClickListener {
                onProgramClick.selectProgram(adapterPosition)
            }

            learnMoreBtn.setOnClickListener {
                onProgramClick.learnMore(adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.program_item, parent, false),
            onProgramClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listOfPrograms[position].program_title
        holder.days.text = holder.itemView.context.getString(
            R.string.program_library_item_days_a_week,
            listOfPrograms[position].days
        )
        holder.weeks.text = holder.itemView.context.getString(
            R.string.program_library_item_weeks,
            listOfPrograms[position].weeks
        )
        if (listOfPrograms[position].difficulty != null) {
            holder.progressBar.progress = listOfPrograms[position].difficulty?.times(33)!!
        }

    }

    override fun getItemCount(): Int {
        return listOfPrograms.size
    }

    fun setData(list: List<Program>) {
        listOfPrograms.clear()
        listOfPrograms.addAll(list)
        notifyDataSetChanged()
    }

}