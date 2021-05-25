package com.example.cactusnotes.notes

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class NoteItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacing = 32 //in pixels

        val position = parent.getChildAdapterPosition(view)
        val itemIsAtFirstRow = position < 2

        val itemIsAtLeftColumn =
            (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex == 0

        if (itemIsAtFirstRow) {
            outRect.top = spacing
        }

        if (itemIsAtLeftColumn) {
            outRect.left = spacing
        }

        outRect.bottom = spacing

        outRect.right = spacing

    }
}