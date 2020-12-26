package com.jcchrun.albums.presentation.albums.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val gridLayoutColumns: Int, private val spaceHeight: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        with(outRect) {
            top = when {
                position <= gridLayoutColumns -1 -> spaceHeight
                else -> 0
            }
            left = spaceHeight / 2
            right = spaceHeight / 2
            bottom = spaceHeight
        }
    }
}