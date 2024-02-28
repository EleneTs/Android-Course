package com.etsiramua.todolist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.etsiramua.todolist.data.entity.Subtask

@Dao
interface SubtaskDao {
    @Query("SELECT * FROM subtasks WHERE todoListId = :todoListId")
    fun getSubtasksForTodoList(todoListId: Int): List<Subtask>

    @Query("SELECT COUNT(*) FROM subtasks WHERE subtask_title = :title AND todoListId = :id")
    fun getItemWithNameAndId(title: String, id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubtask(subtask: Subtask)

    @Update
    fun updateSubtask(subtask: Subtask)

    @Delete
    fun deleteSubtask(subtask: Subtask)

    @Query("UPDATE subtasks SET is_done = 1 WHERE todoListId = :todoId AND subtask_title = :subtaskTitle")
    fun subtaskDone(todoId: Int, subtaskTitle: String)

    @Query("UPDATE subtasks SET subtask_title = :newTitle WHERE id = :id")
    fun subtaskChangeName(newTitle: String, id: Int)
}