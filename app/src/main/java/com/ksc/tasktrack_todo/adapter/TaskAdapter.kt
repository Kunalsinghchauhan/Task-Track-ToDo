package com.ksc.tasktrack_todo.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ksc.tasktrack_todo.R
import com.ksc.tasktrack_todo.UpdateTaskActivity
import com.ksc.tasktrack_todo.model.CardViewTask
import kotlinx.coroutines.selects.select

class TaskAdapter(private val data: List<CardViewTask>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
        val priority: LinearLayout = itemView.findViewById(R.id.ll_task_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        when (data[position].priority.lowercase()) {
            "high" -> holder.priority.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.priority.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.priority.setBackgroundColor(Color.parseColor("#72d691"))
        }

        holder.tvTaskTitle.text = data[position].title

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }

    }
}