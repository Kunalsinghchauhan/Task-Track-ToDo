package com.ksc.tasktrack_todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ksc.tasktrack_todo.model.CardViewTask


@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table")
    suspend fun getAllTask(): List<CardViewTask>

    @Query("SELECT * FROM todo_table LIMIT 1 OFFSET :position")
    suspend fun getTaskByPosition(position: Int): CardViewTask
}