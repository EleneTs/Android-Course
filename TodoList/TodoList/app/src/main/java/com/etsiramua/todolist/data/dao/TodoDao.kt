package com.etsiramua.todolist.data.dao

import androidx.room.*
import com.etsiramua.todolist.data.entity.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos(): List<Todo>

    @Query("SELECT * FROM todos WHERE title like :title")
    fun getTodoByName(title: String): Todo

    @Query("SELECT * FROM todos WHERE id like :id")
    fun getTodoById(id: Int): Todo

    @Query("SELECT * FROM todos WHERE is_pinned = 1")
    fun getPinnedTodos(): List<Todo>

    @Query("SELECT * FROM todos WHERE is_pinned = 0")
    fun getUnpinnedTodos(): List<Todo>

    @Query("UPDATE todos SET is_pinned = :pinned WHERE id = :id")
    fun updatePinnedStatus(id: Int, pinned: Int)

    @Query("UPDATE todos SET title = :name WHERE id = :id")
    fun updateTodoName(name: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Update
    fun update(todo: Todo)
}