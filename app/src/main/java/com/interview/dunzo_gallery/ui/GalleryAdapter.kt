package com.interview.dunzo_gallery.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.interview.dunzo_gallery.model.GalleryImageConfig
import com.interview.dunzo_gallery.ui.vh.GalleryItemVH
import com.interview.dunzo_gallery.ui.view.GalleryRowContainer

class GalleryAdapter(val context: Context) :
    ListAdapter<List<GalleryImageConfig>, GalleryItemVH>(GalleryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemVH {
        val view = GalleryRowContainer(context)
        return GalleryItemVH(view)
    }

    override fun onBindViewHolder(holder: GalleryItemVH, position: Int) {
        holder.setData(getItem(position))
    }

    fun setData(items: List<List<GalleryImageConfig>>) {
        submitList(items)
    }

}

class GalleryDiffUtil : DiffUtil.ItemCallback<List<GalleryImageConfig>>() {
    override fun areItemsTheSame(
        oldItem: List<GalleryImageConfig>,
        newItem: List<GalleryImageConfig>
    ): Boolean {
        return areContentsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(
        oldItem: List<GalleryImageConfig>,
        newItem: List<GalleryImageConfig>
    ): Boolean {
        if (oldItem.size != newItem.size) {
            return false
        } else {
            var isSame = true
            for (it in 0 until oldItem.size) {
                if (!isItemSame(oldItem[it], newItem[it])) {
                    isSame = false
                    break
                }
            }
            return isSame
        }
    }

    private fun isItemSame(o1: GalleryImageConfig, o2: GalleryImageConfig): Boolean {
        return o1.imageUrl == o2.imageUrl && o1.title == o2.title
    }
}
