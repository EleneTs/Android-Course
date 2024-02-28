package com.etsiramua.todolist.firstPage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TodoListMarginDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = margin
        outRect.left = margin
        outRect.top = margin
        outRect.bottom = margin

    }
}