package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private var currentDay = 1
    private var currentWeek = 1
    private var currentProgram = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = Adapter(this)
        val recyclerView : RecyclerView = findViewById(R.id.recycler)

        val db = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java, "database-name"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .createFromAsset("database/mydb.db")
            .build()

        val currentProgramId = 1
        val listOfPrograms = db.programDao().getAllExercisesAndPrograms()

        var listOfExercise = emptyList<Exercise>()

        listOfPrograms.forEach {
            if (it.program.program_id == currentProgramId){
                listOfExercise = it.listOfExercises
            }
        }

        val listOfExIds = ArrayList<Int>()
        listOfExercise.forEach {
            listOfExIds.add(it.exercise_id)
        }

        val listOfExerciseHasLoad = db.programDao().getAllExercisesAndLoads()
        val listOfToday = findTodayExercises(listOfExerciseHasLoad, listOfExIds)



        findViewById<TextView>(R.id.currentDayTV).text = currentDay.toString()
        findViewById<TextView>(R.id.currentWeekTV).text = currentWeek.toString()
        findViewById<TextView>(R.id.currentProgramTV).text = currentProgram.toString()


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setData(listOfToday.toList())

        val right = findViewById<TextView>(R.id.right)
        right.setOnClickListener {
            if (currentDay == 7){
                currentDay = 1
                currentWeek++
            }else {
                currentDay++
            }
            findViewById<TextView>(R.id.currentDayTV).text = currentDay.toString()
            findViewById<TextView>(R.id.currentWeekTV).text = currentWeek.toString()
            Log.i("NEWDAYRIGHT", currentDay.toString())
            adapter.setData(findTodayExercises(listOfExerciseHasLoad, listOfExIds))
        }

        val left = findViewById<TextView>(R.id.left)
        left.setOnClickListener {
            if (currentDay == 1 && currentWeek > 1){
                currentDay = 7
                currentWeek--
            }else{
                currentDay--
            }
            findViewById<TextView>(R.id.currentDayTV).text = currentDay.toString()
            findViewById<TextView>(R.id.currentWeekTV).text = currentWeek.toString()
            Log.i("NEWDAYLEFT", currentDay.toString())
            adapter.setData(findTodayExercises(listOfExerciseHasLoad, listOfExIds))
        }

    }

    fun findTodayExercises(listOfExerciseHasLoad : List<ExerciseHasLoad>, listOfExIds : List<Int>) : List<ExerciseHasLoad>{
        val listOfToday = ArrayList<ExerciseHasLoad>()
        listOfExerciseHasLoad.forEachIndexed { index,item ->
            if (listOfExIds.contains(item.exercise.exercise_id) && item.exercise.day == currentDay && item.exercise.week == currentWeek){
                listOfToday.add(item)
            }
        }
        return listOfToday
    }

}

class Adapter(private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var listOfExercisesHasLoad = listOf<ExerciseHasLoad>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var id : TextView = itemView.findViewById(R.id.idTV)
        var title : TextView = itemView.findViewById(R.id.titleTV)
        var loadSize : TextView = itemView.findViewById(R.id.loadSizeTV)
        var childRecycler : RecyclerView = itemView.findViewById(R.id.childRecycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listOfExercisesHasLoad[position]
        holder.id.text = currentItem.exercise.exercise_id.toString()
        holder.title.text = currentItem.exercise.exercise_title
        holder.loadSize.text = currentItem.listOfLoads.size.toString()

        val adapter = ChildAdapter(context)
        holder.childRecycler.adapter = adapter
        holder.childRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.setData(currentItem.listOfLoads)
    }

    override fun getItemCount(): Int {
        return listOfExercisesHasLoad.size
    }

    fun setData(it : List<ExerciseHasLoad>){
        listOfExercisesHasLoad = it
        notifyDataSetChanged()
    }

}

class ChildAdapter(private val context: Context) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    var listOfLoads = emptyList<Load>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var reps : TextView = itemView.findViewById(R.id.repsTV)
        var sets : TextView = itemView.findViewById(R.id.setsTV)
        var intensity : TextView = itemView.findViewById(R.id.intensityTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.child, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.reps.text = listOfLoads[position].reps.toString()
        holder.sets.text = listOfLoads[position].sets.toString()
        holder.intensity.text = listOfLoads[position].intensity.toString()
    }

    override fun getItemCount(): Int {
        return listOfLoads.size
    }

    fun setData(it : List<Load>){
        listOfLoads = it
        notifyDataSetChanged()
    }

}
