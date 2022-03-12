package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.ExerciseHasLoad
import com.example.myapplication.R

class ExerciseAdapter(
    private val context: Context,
    private val onExerciseClick: OnExerciseClick
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
//    На данный момент подразумевается, что максимальное количество упражнений в один день - 8,
//    так как абсолютное большинство программ попадают в данный диапазон
    val listOfLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    val listOfExercisesHasLoad = mutableListOf<ExerciseHasLoad>()

    interface OnExerciseClick{
        fun onFinishClick(pos : Int)
    }

    class ViewHolder(
        itemView: View,
        OnExerciseClick: OnExerciseClick
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.exerciseTitleTV)
        val letter: TextView = itemView.findViewById(R.id.letterTV)
        val childRecycler: RecyclerView = itemView.findViewById(R.id.childRecycler)
        val finishButton : RadioButton = itemView.findViewById(R.id.finishButton)

        init {
            finishButton.setOnClickListener {
                OnExerciseClick.onFinishClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false),
            onExerciseClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listOfExercisesHasLoad[position]

        holder.title.text = currentItem.exercise.exercise_title
        holder.letter.text = listOfLetters[position]

        val adapter = LoadAdapter()
        holder.childRecycler.adapter = adapter
        holder.childRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setData(currentItem.listOfLoads)

    }

    override fun getItemCount(): Int {
        return listOfExercisesHasLoad.size
    }

    fun setData(it: List<ExerciseHasLoad>) {
        listOfExercisesHasLoad.clear()
        listOfExercisesHasLoad.addAll(it)
        notifyDataSetChanged()
    }

}