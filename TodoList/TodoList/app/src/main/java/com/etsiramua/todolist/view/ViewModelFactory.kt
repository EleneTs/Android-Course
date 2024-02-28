package com.etsiramua.todolist.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.etsiramua.todolist.Repository
import com.etsiramua.todolist.data.TodoDatabase

class ViewModelFactory(var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(
                Repository(Room.databaseBuilder(application,
                    TodoDatabase::class.java, "DatabaseForTodos")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build())
            ) as T
        }
        throw IllegalArgumentException("ViewModel class not detected")
    }


}