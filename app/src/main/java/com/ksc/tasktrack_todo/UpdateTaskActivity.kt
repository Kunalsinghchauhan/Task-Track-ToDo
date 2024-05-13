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
import com.ksc.tasktrack_todo.model.DataObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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

        val position = intent.getIntExtra("id", -1)
        if (position != -1) {
            val title = DataObject.getData(position).title
            val priority = DataObject.getData(position).priority
            binding.etUpdateTaskTitle.setText(title)
            binding.etUpdateTaskPriority.setText(priority)

            binding.btnUpdate.setOnClickListener {
                DataObject.updateData(
                    position,
                    binding.etUpdateTaskTitle.text.toString(),
                    binding.etUpdateTaskPriority.text.toString()
                )
                GlobalScope.launch {
                    database.todoDao().update(
                        Todo(
                            position + 1, binding.etUpdateTaskTitle.text.toString(),
                            binding.etUpdateTaskPriority.text.toString()
                        )
                    )
                }
                Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show()
                myIntent()
                finish()
            }
            binding.btnDelete.setOnClickListener {
                DataObject.deleteData(position)
                GlobalScope.launch {
                    database.todoDao().delete(
                        Todo(
                            position + 1, binding.etUpdateTaskTitle.text.toString(),
                            binding.etUpdateTaskPriority.text.toString()
                        )
                    )
                }
                myIntent()
                finish()
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
}