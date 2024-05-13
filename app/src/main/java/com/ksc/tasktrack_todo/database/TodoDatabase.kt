package com.ksc.tasktrack_todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)

abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

}