package com.brxq.gyminstructor.ui.exercise

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brxq.gyminstructor.model.ExerciseHasLoad
import com.brxq.gyminstructor.R
import com.brxq.gyminstructor.ui.exercise.load.LoadAdapter

class ExerciseAdapter(
    private val context: Context,
    private val onExerciseClick: OnExerciseClick
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private val listOfLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H")
    val listOfExercisesHasLoad = mutableListOf<ExerciseHasLoad>()

    interface OnExerciseClick {
        fun onFinishClick(pos: Int)
    }

    class ViewHolder(
        itemView: View,
        OnExerciseClick: OnExerciseClick
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.exerciseTitleTV)
        val letter: TextView = itemView.findViewById(R.id.letterTV)
        val childRecycler: RecyclerView = itemView.findViewById(R.id.childRecycler)
        val finishButton: RadioButton = itemView.findViewById(R.id.finishButton)

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



        Log.i(
            "STRING_ID",
            context.resources.getIdentifier(
                listOfExercisesHasLoad[position].exercise.exercise_title,
                "string",
                context.packageName
            ).toString()
        )

        if (
            context.resources.getIdentifier(
                listOfExercisesHasLoad[position].exercise.exercise_title,
                "string",
                context.packageName
            ) != 0
        ) {
            holder.title.text =
                context.resources.getString(
                    context.resources.getIdentifier(
                        listOfExercisesHasLoad[position].exercise.exercise_title,
                        "string",
                        context.packageName
                    )
                )
        }


        holder.letter.text = listOfLetters[position]
        holder.finishButton.isChecked = false
        holder.finishButton.isClickable = true
        val adapter = LoadAdapter(context)
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

    fun updateData(pos: Int) {
        listOfExercisesHasLoad.removeAt(pos)
        notifyItemRemoved(pos)
    }

}