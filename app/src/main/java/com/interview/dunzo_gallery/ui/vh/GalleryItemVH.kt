package com.interview.dunzo_gallery.ui.vh

import androidx.recyclerview.widget.RecyclerView
import com.interview.dunzo_gallery.model.GalleryImageConfig
import com.interview.dunzo_gallery.ui.view.GalleryRowContainer

class GalleryItemVH(val view: GalleryRowContainer) : RecyclerView.ViewHolder(view) {
    fun setData(config: List<GalleryImageConfig>) {
        view.updateView(config)
    }
}
