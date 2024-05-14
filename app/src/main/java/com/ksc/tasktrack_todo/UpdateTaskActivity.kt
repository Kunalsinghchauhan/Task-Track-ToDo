package com.ksc.tasktrack_todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ksc.tasktrack_todo.database.Todo
import com.ksc.tasktrack_todo.database.TodoDatabase
import com.ksc.tasktrack_todo.databinding.ActivityUpdateTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var database: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            "todo_table"
        ).build()

        var title = ""
        var priority = ""
        var priorityText = ""
        var position = intent.getIntExtra("id", -1)

        GlobalScope.launch {
            val task = database.todoDao().getTaskByPosition(position)
            withContext(Dispatchers.Main) {
                binding.etUpdateTaskTitle.setText(task.title)
                binding.etUpdateTaskPriority.setText(task.priority)
            }
        }

        binding.btnUpdate.setOnClickListener {
            GlobalScope.launch {
                database.todoDao().update(
                    Todo(
                        position + 1, binding.etUpdateTaskTitle.text.toString(),
                        binding.etUpdateTaskPriority.text.toString(),
                        priorityText
                    )
                )
            }
            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show()
            myIntent()
            finish()
        }
        binding.btnDelete.setOnClickListener {
            GlobalScope.launch {
                database.todoDao().delete(
                    Todo(
                        position + 1, binding.etUpdateTaskTitle.text.toString(),
                        binding.etUpdateTaskPriority.text.toString(),
                        priorityText
                    )
                )
                Log.i("kunall", "Update Position $position")
            }
            myIntent()
            finish()
        }

    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        database.close()
        GlobalScope.launch {  }.cancel()
    }
}