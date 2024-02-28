package com.etsiramua.todolist.secondPage

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.etsiramua.todolist.R
import com.etsiramua.todolist.data.entity.FullTodoModel
import com.etsiramua.todolist.data.entity.Subtask
import com.etsiramua.todolist.data.entity.SubtaskModel
import com.etsiramua.todolist.data.entity.Todo
import com.etsiramua.todolist.view.TodoListViewModel
import com.etsiramua.todolist.view.ViewModelFactory

class OneTodoActivity : AppCompatActivity(), SubtaskListener {
    val viewModel: TodoListViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application)).get(TodoListViewModel::class.java)
    }

    private var doneList = mutableListOf<SubtaskModel>()
    private var notDoneList = mutableListOf<SubtaskModel>()

    private val doneAdapter = OneTodoAdapter(doneList, this)
    private val notDoneAdapter = OneTodoAdapter(notDoneList, this)

    private var isPinned = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_todo)


        findViewById<RecyclerView>(R.id.checkedSubtasks).adapter = doneAdapter
        findViewById<RecyclerView>(R.id.subtasks).adapter = notDoneAdapter


        findViewById<EditText>(R.id.todoTitle).setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_TAB)) {
                val enteredText = findViewById<EditText>(R.id.todoTitle).text.toString()
                if (viewModel.getTodoByName(enteredText) == null) {
                    viewModel.createTodo(
                        FullTodoModel(
                            id = 0,
                            title = enteredText,
                            isPinned = false
                        )
                    )
                }
                true
            } else {
                false
            }
        }

        val id = intent.getIntExtra("id", -1)
        if (id != -1) {
            val todo = viewModel.getFullTodo(id)
            findViewById<EditText>(R.id.todoTitle).setText(todo.title)

            doneList = viewModel.doneSubtasks(todo)
            notDoneList = viewModel.notDoneSubtasks(todo)
            doneAdapter.list = doneList
            notDoneAdapter.list = notDoneList
            if (todo.isPinned) {
                findViewById<ImageButton>(R.id.pinButton).setBackgroundResource(R.drawable.ic_pinned)
            } else {
                findViewById<ImageButton>(R.id.pinButton).setBackgroundResource(R.drawable.ic_pin)
            }
            // Edit Title Case
            findViewById<EditText>(R.id.todoTitle).setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_TAB)) {
                    val enteredText = findViewById<EditText>(R.id.todoTitle).text.toString()
                    viewModel.updateTodoName(enteredText, id)
                    true
                } else {
                    false
                }
            }
            isPinned = todo.isPinned
            doneAdapter.notifyDataSetChanged()
            notDoneAdapter.notifyDataSetChanged()
        }
    }

    fun pinButtonAction(view: View) {
        if (isPinned) {
            findViewById<ImageButton>(R.id.pinButton).setBackgroundResource(R.drawable.ic_pin)
            isPinned = false
        } else {
            findViewById<ImageButton>(R.id.pinButton).setBackgroundResource(R.drawable.ic_pinned)
            isPinned = true
        }

        val todo = getCurrentTodo()
        viewModel.changeTodoPinStatus(todo)
    }

    fun backButtonAction(view: View) {
        val todo = getCurrentTodo()

        intent.putExtra("refresh", true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun addSubtaskAction(view: View) {
//        if (notDoneList.isNotEmpty()) {
//            val oldSubtask = notDoneList.last()
//            if (!viewModel.isSubtaskPresent(oldSubtask)) {
//                val subtask = Subtask(
//                    id = 0,
//                    todoListId = getCurrentTodo().id,
//                    title = oldSubtask.title,
//                    isDone = oldSubtask.isDone
//                )
//                viewModel.insertSubtask(subtask)
//                notDoneAdapter.notifyDataSetChanged()
//            }
//        }
        notDoneList.add(SubtaskModel("", false, 0))
        notDoneAdapter.list = notDoneList
        notDoneAdapter.notifyDataSetChanged()
    }


    override fun onCheckClick(subtask: SubtaskModel, position: Int) {
        var id = subtask.todoId
        if (id == 0) {
            id = getCurrentTodo().id
        }
        viewModel.subtaskDone(subtaskTitle = subtask.title, todoId = id)
        doneList = viewModel.doneSubtasks(viewModel.getFullTodo(id))
        notDoneList = viewModel.notDoneSubtasks(viewModel.getFullTodo(id))
        doneAdapter.list = doneList
        notDoneAdapter.list = notDoneList

        doneAdapter.notifyDataSetChanged()
        notDoneAdapter.notifyDataSetChanged()

    }

    override fun onDeleteClick(subtask: SubtaskModel, position: Int) {
        var subtaskId = subtask.id
        if (subtaskId != 0) {
            val subtaskEntity = Subtask(
                id = subtaskId,
                todoListId = subtask.todoId,
                title = subtask.title,
                isDone = subtask.isDone
            )
            viewModel.deleteSubtask(subtaskEntity)
            doneList = viewModel.doneSubtasks(viewModel.getFullTodo(subtask.todoId))
            notDoneList = viewModel.notDoneSubtasks(viewModel.getFullTodo(subtask.todoId))
            doneAdapter.list = doneList
            notDoneAdapter.list = notDoneList

            doneAdapter.notifyDataSetChanged()
            notDoneAdapter.notifyDataSetChanged()

        }
    }

    override fun createSubtask(subtask: SubtaskModel, position: Int) {
        val todo = getCurrentTodo()
        viewModel.insertSubtask(
            Subtask(
                id = 0,
                todoListId = todo.id,
                title = subtask.title,
                isDone = subtask.isDone
            )
        )
    }

    override fun updateSubtaskName(newName: String, id: Int) {
        viewModel.subtaskChangeName(newName, id)

        doneList = viewModel.doneSubtasks(viewModel.getFullTodo(getCurrentTodo().id))
        notDoneList = viewModel.notDoneSubtasks(viewModel.getFullTodo(getCurrentTodo().id))
        doneAdapter.list = doneList
        notDoneAdapter.list = notDoneList

        doneAdapter.notifyDataSetChanged()
        notDoneAdapter.notifyDataSetChanged()
    }

    private fun getCurrentTodo(): Todo {
        val todoTitle = findViewById<EditText>(R.id.todoTitle).text.toString()
        var todo = viewModel.getTodoByName(todoTitle)
        if (todo == null) {
            return viewModel.createTodo(
                FullTodoModel(
                    id = 0,
                    title = todoTitle,
                    isPinned = false
                )
            )
        }
        return todo
    }

}


interface SubtaskListener {
    fun onCheckClick(subtask: SubtaskModel, position: Int)

    fun onDeleteClick(subtask: SubtaskModel, position: Int)

    fun createSubtask(subtask: SubtaskModel, position: Int)

    fun updateSubtaskName(newName: String, id: Int)
}