package com.ksc.tasktrack_todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.ksc.tasktrack_todo.adapter.TaskAdapter
import com.ksc.tasktrack_todo.database.Todo
import com.ksc.tasktrack_todo.database.TodoDatabase
import com.ksc.tasktrack_todo.databinding.ActivityMainBinding
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


        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                GlobalScope.launch {
                    database.todoDao().delete(
                        Todo(
                            position + 1, " binding.etUpdateTaskTitle.text.toString()",
                            "binding.etUpdateTaskPriority.text.toString()",
                            "priorityText"
                        )
                    )
                }
                Snackbar.make(
                    binding.root,
                    "Task Deleted",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.homeRecyclerView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
        GlobalScope.launch { }.cancel()
    }
}
