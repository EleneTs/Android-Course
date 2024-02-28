package com.etsiramua.todolist.firstPage

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.etsiramua.todolist.R
import com.etsiramua.todolist.view.TodoListViewModel
import com.etsiramua.todolist.view.ViewModelFactory
import com.etsiramua.todolist.data.entity.FullTodoModel
import com.etsiramua.todolist.data.entity.SubtaskModel
import com.etsiramua.todolist.secondPage.OneTodoActivity

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val REQUEST_CODE = 16

    val viewModel: TodoListViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application)).get(TodoListViewModel::class.java)
    }

    var unpinnedAdapter = TodoListAdapter(mutableListOf(), this)
    var pinnedAdapter = TodoListAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModel.clearAllTables()

        loadData()

        unpinnedAdapter = TodoListAdapter(viewModel.getOtherTodos(), this)
        pinnedAdapter = TodoListAdapter(viewModel.getPinnedTodos(), this)

        findViewById<RecyclerView>(R.id.otherTodos).adapter = unpinnedAdapter
        findViewById<RecyclerView>(R.id.pinnedTodos).adapter = pinnedAdapter

        findViewById<RecyclerView>(R.id.otherTodos).addItemDecoration(TodoListMarginDecoration(8))
        findViewById<RecyclerView>(R.id.pinnedTodos).addItemDecoration(TodoListMarginDecoration(8))


        findViewById<EditText>(R.id.searchTodo).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                loadSearchData(text)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val refreshData = data?.getBooleanExtra("refresh", false) ?: false
            if (refreshData) {
                loadData()
            }
        }
    }

    private fun loadData() {
        loadSearchData("")
    }

    private fun loadSearchData(title: String) {
        findViewById<TextView>(R.id.pinned).visibility = View.GONE
        findViewById<TextView>(R.id.other).visibility = View.GONE
        val pinned = viewModel.getPinnedTodosByName(title)
        val other = viewModel.getOtherTodosByName(title)
        if(!pinned.isEmpty()) {
            findViewById<TextView>(R.id.pinned).visibility = View.VISIBLE
        }
        if(!other.isEmpty()) {
            findViewById<TextView>(R.id.other).visibility = View.VISIBLE
        }
        unpinnedAdapter.list = other as MutableList<FullTodoModel>
        unpinnedAdapter.notifyDataSetChanged()
        pinnedAdapter.list = pinned as MutableList<FullTodoModel>
        pinnedAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(data: FullTodoModel) {
        val intent = Intent(this, OneTodoActivity::class.java)
        intent.putExtra("id", data.id)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun AddButtonAction(view: View) {
        var intent = Intent(this, OneTodoActivity::class.java)
//        startActivity(intent)
        startActivityForResult(intent, REQUEST_CODE)
    }
}

interface OnItemClickListener {
    fun onItemClick(data: FullTodoModel)
}