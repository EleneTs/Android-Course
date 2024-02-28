package com.etsiramua.todolist

import com.etsiramua.todolist.data.TodoDatabase
import com.etsiramua.todolist.data.entity.Subtask
import com.etsiramua.todolist.data.entity.SubtaskModel
import com.etsiramua.todolist.data.entity.Todo

class Repository(private val dataBase: TodoDatabase) {

    fun getAllTodos(): List<Todo> {
        return dataBase.todoDao().getAllTodos()
    }

    fun clearAllTables() {
        dataBase.clearAllTables()
    }

    fun getTodoByName(title: String): Todo {
        return dataBase.todoDao().getTodoByName(title)
    }

    fun changePinStatus(todo: Todo) {
        if (todo.isPinned) {
            dataBase.todoDao().updatePinnedStatus(todo.id, 0)
        } else {
            dataBase.todoDao().updatePinnedStatus(todo.id, 1)
        }
    }

    fun subtaskDone(todoId: Int, subtaskTitle: String) {
        dataBase.subtaskDao().subtaskDone(todoId, subtaskTitle)
    }

    fun subtaskChangeName(newTitle: String, id: Int) {
        dataBase.subtaskDao().subtaskChangeName(newTitle = newTitle, id = id)
    }

    fun getTodoById(id: Int): Todo {
        return dataBase.todoDao().getTodoById(id)
    }

    fun isSubtaskPresent(subtaskModel: SubtaskModel): Boolean {
        return dataBase.subtaskDao().getItemWithNameAndId(subtaskModel.title, subtaskModel.todoId) != 0
    }

    fun getPinnedTodos(): List<Todo> {
        return dataBase.todoDao().getPinnedTodos()
    }

    fun getUnpinnedTodos(): List<Todo> {
        return dataBase.todoDao().getUnpinnedTodos()
    }

    fun getSubtasksOfTodo(id: Int): List<Subtask> {
        return dataBase.subtaskDao().getSubtasksForTodoList(id)
    }

    fun insertTodo(todo: Todo) {
        return dataBase.todoDao().insert(todo)
    }

    fun deleteTodo(todo: Todo) {
        dataBase.todoDao().delete(todo)
    }

    fun changeTodoName(name: String, id: Int) {
        dataBase.todoDao().updateTodoName(name, id)
    }

    fun updateTodo(todo: Todo) {
        dataBase.todoDao().update(todo)
    }

    fun insertSubtask(subtask: Subtask) {
        dataBase.subtaskDao().insertSubtask(subtask)
    }

    fun deleteSubtask(subtask: Subtask) {
        dataBase.subtaskDao().deleteSubtask(subtask)
    }

    fun updateSubtask(subtask: Subtask) {
        dataBase.subtaskDao().updateSubtask(subtask)
    }



}