package com.ksc.tasktrack_todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ksc.tasktrack_todo.adapter.TaskAdapter
import com.ksc.tasktrack_todo.database.TodoDatabase
import com.ksc.tasktrack_todo.databinding.ActivityMainBinding
import com.ksc.tasktrack_todo.model.CardViewTask
import com.ksc.tasktrack_todo.model.DataObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: TodoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_table"
        ).build()

        GlobalScope.launch(Dispatchers.Main) {
            DataObject.list = database.todoDao().getAllTask() as MutableList<CardViewTask>
        }

        binding.homeRecyclerView.layoutManager = LinearLayoutManager(this)
        GlobalScope.launch(Dispatchers.Main) {
            val data = database.todoDao().getAllTask()
            binding.homeRecyclerView.adapter = TaskAdapter(data)

            if (data.isEmpty()) {
                binding.root.setBackgroundResource(R.drawable.no_task_background)
            } else {
                binding.root.background = null
            }
        }

        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }
}
