package com.ksc.tasktrack_todo.model

object DataObject {
    var list = mutableListOf<CardViewTask>()

    fun setData(title: String, priority: String) {
        list.add(CardViewTask(title, priority))
    }

    fun getAllData(): List<CardViewTask> {
        return list
    }

    fun getData(position: Int): CardViewTask {
        return list[position]
    }

    fun deleteData(position: Int) {
        list.removeAt(position)
    }

    fun updateData(position: Int, title: String, priority: String) {
        list[position].title = title
        list[position].priority = priority
    }
}