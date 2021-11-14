package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChildAdapter : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    var listOfLoads = emptyList<Load>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sets: TextView = itemView.findViewById(R.id.setsTV)
        var reps: TextView = itemView.findViewById(R.id.repsTV)
        var rpe : TextView = itemView.findViewById(R.id.rpeTV)
        var load : TextView = itemView.findViewById(R.id.loadTV)
        var intensity: TextView = itemView.findViewById(R.id.intensityTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.child, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0){
            holder.sets.text = "Sets"
            holder.reps.text = "Reps"
            holder.rpe.text = "Rpe "
            holder.load.text = "Load"
            holder.intensity.text = "%1RM"
        }else{
            holder.sets.text = listOfLoads[position-1].sets.toString()
            holder.reps.text = listOfLoads[position-1].reps.toString()
            holder.rpe.text = "6"
            holder.load.text = "160"
            holder.intensity.text = listOfLoads[position-1].intensity.toString()
        }
    }

    override fun getItemCount(): Int {
        return listOfLoads.size+1
    }

    fun setData(it: List<Load>) {
        listOfLoads = it
        notifyDataSetChanged()
    }

}