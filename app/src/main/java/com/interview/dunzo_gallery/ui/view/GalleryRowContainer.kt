package com.interview.dunzo_gallery.ui.view

import android.util.Log
import android.content.Context
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.interview.dunzo_gallery.model.GalleryImageConfig
import com.interview.dunzo_gallery.util.Constants

class GalleryRowContainer(context: Context) : LinearLayout(context) {

    init {
        orientation = HORIZONTAL
    }

    fun updateView(config: List<GalleryImageConfig>) {
        Log.d("gal", "this row has: ${config.size} items")
        config.forEach {
            val itemView = GalleryItemView(context)
            val dimension = (0.95 * (getViewportWidth() / Constants.MAX_IMAGES_PER_ROW)).toInt()
            val margin  = dimension / 95
            val lp = ConstraintLayout.LayoutParams(dimension, dimension)
            lp.setMargins(margin, margin, margin, margin)
            itemView.layoutParams = lp
            itemView.updateView(it)
            addView(itemView)
        }
    }

    fun getViewportWidth(): Int {
        return context.getResources().getDisplayMetrics().widthPixels
    }
}