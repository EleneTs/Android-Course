package com.etsiramua.todolist.view

import androidx.lifecycle.ViewModel
import com.etsiramua.todolist.Repository
import com.etsiramua.todolist.data.entity.FullTodoModel
import com.etsiramua.todolist.data.entity.Subtask
import com.etsiramua.todolist.data.entity.SubtaskModel
import com.etsiramua.todolist.data.entity.Todo

class TodoListViewModel(private val repository: Repository): ViewModel() {
    fun createTodo(todo: FullTodoModel): Todo {
        val todoEntity = Todo(id = 0, isPinned = todo.isPinned, title = todo.title)
        repository.insertTodo(todoEntity)
        val id = repository.getTodoByName(todo.title).id

        todo.subtasks.forEach {
            repository.insertSubtask(Subtask(id = 0, todoListId = id, isDone = it.isDone, title = it.title))
        }
        return repository.getTodoByName(todo.title)
    }

    fun updateTodoName(name: String, id: Int) {
        repository.changeTodoName(name, id)
    }

    fun isSubtaskPresent(subtaskModel: SubtaskModel): Boolean {
        return repository.isSubtaskPresent(subtaskModel)
    }

    fun changeTodoPinStatus(todo: Todo) {
        repository.changePinStatus(todo)
    }


    fun subtaskDone(subtaskTitle: String, todoId: Int) {
        repository.subtaskDone(subtaskTitle = subtaskTitle, todoId = todoId)
    }

    fun deleteSubtask(subtask: Subtask) {
        repository.deleteSubtask(subtask)
    }

    fun subtaskChangeName(newTitle: String, id: Int) {
        repository.subtaskChangeName(newTitle = newTitle, id = id)
    }

    fun insertSubtask(subtask: Subtask) {
        repository.insertSubtask(subtask)
    }

    fun getTodoByName(title: String): Todo {
        return repository.getTodoByName(title)
    }

    fun getPinnedTodos(): MutableList<FullTodoModel> {
        return getFullTodos().filter { it.isPinned }.toMutableList()
    }

    fun getOtherTodos(): MutableList<FullTodoModel> {
        return getFullTodos().filter { !it.isPinned }.toMutableList()
    }

    fun getPinnedTodosByName(title: String): List<FullTodoModel> {
        return getPinnedTodos().filter {
            it.title.startsWith(prefix = title, ignoreCase = true)
        }
    }

    fun getOtherTodosByName(title: String): List<FullTodoModel> {
        return getOtherTodos().filter {
            it.title.startsWith(prefix = title, ignoreCase = true)
        }
    }


    fun clearAllTables() {
        return repository.clearAllTables()
    }

    fun doneSubtasks(todo: FullTodoModel): MutableList<SubtaskModel> {
        val doneSubtasks = mutableListOf<SubtaskModel>()
        todo.subtasks.forEach {
            if(it.isDone) {
                doneSubtasks.add(it)
            }
        }
        return doneSubtasks
    }

    fun notDoneSubtasks(todo: FullTodoModel): MutableList<SubtaskModel> {
        val notDoneSubtasks = mutableListOf<SubtaskModel>()
        todo.subtasks.forEach {
            if(!it.isDone) {
                notDoneSubtasks.add(it)
            }
        }
        return notDoneSubtasks
    }

    fun getFullTodo(id: Int): FullTodoModel {
        val todo = repository.getTodoById(id)

        val subtasks = mutableListOf<SubtaskModel>()

        val subtaskFromDb = repository.getSubtasksOfTodo(id)
        subtaskFromDb.forEach {
            val subtaskModel = SubtaskModel(title = it.title, isDone = it.isDone, todoId = todo.id, id = it.id )
            subtasks.add(subtaskModel)
        }

        return FullTodoModel(
            id = todo.id,
            title = todo.title,
            isPinned = todo.isPinned,
            subtasks = subtasks
        )
    }

    fun getFullTodos(): MutableList<FullTodoModel> {
        val fullTodos = mutableListOf<FullTodoModel>()
        val todosFromDb = repository.getAllTodos()
        todosFromDb.forEach { todo ->
            val subtasks = mutableListOf<SubtaskModel>()

            val subtaskFromDb = repository.getSubtasksOfTodo(todo.id)
            subtaskFromDb.forEach {
                val subtaskModel = SubtaskModel(title = it.title, isDone = it.isDone, todoId = todo.id)
                subtasks.add(subtaskModel)
            }

            val fullTodoModel = FullTodoModel(
                id = todo.id,
                title = todo.title,
                isPinned = todo.isPinned,
                subtasks = subtasks
            )
            fullTodos.add(fullTodoModel)
        }

        return fullTodos
    }
}