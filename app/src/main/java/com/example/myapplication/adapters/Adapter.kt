package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ExerciseHasLoad
import com.example.myapplication.R

class Adapter(private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val listOfLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    var listOfExercisesHasLoad = listOf<ExerciseHasLoad>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.exerciseTitleTV)
        var letter: TextView = itemView.findViewById(R.id.letterTV)
        var childRecycler: RecyclerView = itemView.findViewById(R.id.childRecycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listOfExercisesHasLoad[position]

        holder.title.text = currentItem.exercise.exercise_title
        holder.letter.text = listOfLetters[position]

        val adapter = ChildAdapter()
        holder.childRecycler.adapter = adapter
        holder.childRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setData(currentItem.listOfLoads)
    }

    override fun getItemCount(): Int {
        return listOfExercisesHasLoad.size
    }

    fun setData(it: List<ExerciseHasLoad>) {
        listOfExercisesHasLoad = it
        notifyDataSetChanged()
    }

}