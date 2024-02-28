package com.etsiramua.todolist.firstPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.etsiramua.todolist.R
import com.etsiramua.todolist.data.entity.FullTodoModel
import com.etsiramua.todolist.data.entity.SubtaskModel

class TodoListItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var title = itemView.findViewById<TextView>(R.id.parentNote)
    var item1 = itemView.findViewById<CheckBox>(R.id.item1)
    var item2 = itemView.findViewById<CheckBox>(R.id.item2)
    var item3 = itemView.findViewById<CheckBox>(R.id.item3)
    var dots = itemView.findViewById<TextView>(R.id.dots)
    var checkedItemsCount = itemView.findViewById<TextView>(R.id.checkedItems)
}

class TodoListAdapter(var list: MutableList<FullTodoModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<TodoListItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListItemHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return TodoListItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TodoListItemHolder, position: Int) {
        clearVisibility(holder)

        val todo = list[position]
        holder.title.text = todo.title

        val notDoneSubtasks = notDoneSubtasks(todo)
        val doneSubtasks = doneSubtasks(todo)

        if (doneSubtasks.size > 0) {
            holder.checkedItemsCount.visibility = View.VISIBLE
            val doneSubtasksString = "+" + doneSubtasks.size.toString() + "checked items"
            holder.checkedItemsCount.text = doneSubtasksString
        }

        if (notDoneSubtasks.size > 0) {
            holder.item1.visibility = View.VISIBLE
            holder.item1.text = notDoneSubtasks[0].title
        }
        if (notDoneSubtasks.size > 1) {
            holder.item2.visibility = View.VISIBLE
            holder.item2.text = notDoneSubtasks[1].title
        }
        if (notDoneSubtasks.size > 2) {
            holder.item3.visibility = View.VISIBLE
            holder.item3.text = notDoneSubtasks[2].title
        }
        if (notDoneSubtasks.size > 3) {
            holder.dots.visibility = View.VISIBLE
        }


        holder.itemView.setOnClickListener {
            listener.onItemClick(todo)
        }

    }

    private fun clearVisibility(holder: TodoListItemHolder) {
        holder.item1.visibility = View.GONE
        holder.item2.visibility = View.GONE
        holder.item3.visibility = View.GONE
        holder.dots.visibility = View.GONE
        holder.checkedItemsCount.visibility = View.GONE
    }

    private fun doneSubtasks(todo: FullTodoModel): MutableList<SubtaskModel> {
        val doneSubtasks = mutableListOf<SubtaskModel>()
        todo.subtasks.forEach {
            if(it.isDone) {
                doneSubtasks.add(it)
            }
        }
        return doneSubtasks
    }

    private fun notDoneSubtasks(todo: FullTodoModel): MutableList<SubtaskModel> {
        val notDoneSubtasks = mutableListOf<SubtaskModel>()
        todo.subtasks.forEach {
            if(!it.isDone) {
                notDoneSubtasks.add(it)
            }
        }
        return notDoneSubtasks
    }

}
