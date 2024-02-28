package com.etsiramua.todolist.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtasks")
data class Subtask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "todoListId") val todoListId: Int,
    @ColumnInfo(name = "subtask_title") var title: String,
    @ColumnInfo(name = "is_done") var isDone: Boolean
)