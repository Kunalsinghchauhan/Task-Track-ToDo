package com.ksc.tasktrack_todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ksc.tasktrack_todo.database.Todo
import com.ksc.tasktrack_todo.database.TodoDatabase
import com.ksc.tasktrack_todo.databinding.ActivityAddTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var database: TodoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_table"
        ).build()

        binding.btnAdd.setOnClickListener {

            if (binding.etAddTaskTitle.text.toString().trim { it <= ' ' }.isNotEmpty() &&
                binding.etAddTaskPriority.text.trim { it <= ' ' }.isNotEmpty()
            ) {
                val task = binding.etAddTaskTitle.text.toString()
                val priority = binding.etAddTaskPriority.text.toString()
                val priorityText = binding.etAddTaskPriority.text.toString()
                GlobalScope.launch(Dispatchers.Main) {
                    database.todoDao().insert(Todo(0, task, priority, priorityText))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.etAddTaskTitle.error = "Please enter a task"
                binding.etAddTaskPriority.error = "Please enter a priority"
            }
        }
    }
}