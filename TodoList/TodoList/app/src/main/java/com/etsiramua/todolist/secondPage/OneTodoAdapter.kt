package com.etsiramua.todolist.secondPage

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.etsiramua.todolist.R
import com.etsiramua.todolist.data.entity.SubtaskModel
import com.etsiramua.todolist.firstPage.OnItemClickListener
import com.etsiramua.todolist.firstPage.TodoListItemHolder

class SubtaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var check = itemView.findViewById<CheckBox>(R.id.check)
    var subtaskTitle = itemView.findViewById<EditText>(R.id.subtaskTitle)
    var delete = itemView.findViewById<ImageView>(R.id.delete)
}

class OneTodoAdapter(var list: MutableList<SubtaskModel>, private val listener: SubtaskListener) : RecyclerView.Adapter<SubtaskViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.subtask_item,parent,false)
        return SubtaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SubtaskViewHolder, position: Int) {
        if (position == itemCount - 1 && !list[position].isDone) {
            holder.delete.visibility = View.VISIBLE
        } else {
            holder.delete.visibility = View.GONE
        }

        var subtask = list[position]
        holder.check.isChecked = subtask.isDone
        holder.subtaskTitle.setText(subtask.title)

        holder.subtaskTitle.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && (i == KeyEvent.KEYCODE_ENTER || i == KeyEvent.KEYCODE_TAB)) {
                if (list[holder.adapterPosition].todoId != 0) {
                    println("UPDATING?")
                    // anu update
                    val newTitle = holder.subtaskTitle.text.toString()
                    listener.updateSubtaskName(newTitle, list[holder.adapterPosition].id)
                } else {
                    val enteredText = holder.subtaskTitle.text.toString()
                    subtask = SubtaskModel(title = enteredText, isDone = holder.check.isChecked)
                    listener.createSubtask(subtask, position)
                    list[position] = subtask
                }
                true
            } else {
                false
            }
        }


        holder.check.setOnClickListener{
            listener.onCheckClick(subtask, position)
        }
        holder.delete.setOnClickListener {
            listener.onDeleteClick(subtask, position)
        }

    }
}