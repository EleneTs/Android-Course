package com.etsiramua.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.etsiramua.todolist.data.dao.SubtaskDao
import com.etsiramua.todolist.data.dao.TodoDao
import com.etsiramua.todolist.data.entity.Subtask
import com.etsiramua.todolist.data.entity.Todo

@Database(entities = [Todo::class, Subtask::class], version = 3)
abstract class TodoDatabase(): RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun subtaskDao(): SubtaskDao

}