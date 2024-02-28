package com.etsiramua.todolist.data.entity

import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_pinned") var isPinned: Boolean = false,
) {
}


