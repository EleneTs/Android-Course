package com.etsiramua.todolist.data.entity

data class FullTodoModel (
    var id: Int = 0,
    var title: String,
    var isPinned: Boolean = false,
    var subtasks: MutableList<SubtaskModel> = mutableListOf()
)

data class SubtaskModel(
    var title: String,
    var isDone: Boolean,
    var todoId: Int = 0,
    var id: Int = 0
)
