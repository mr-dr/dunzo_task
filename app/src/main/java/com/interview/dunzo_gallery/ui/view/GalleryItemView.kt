package com.interview.dunzo_gallery.ui.view

import android.util.Log
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.interview.dunzo_gallery.R
import com.interview.dunzo_gallery.model.GalleryImageConfig

class GalleryItemView(context: Context) : ConstraintLayout(context) {

    val mImage: ImageView
    val mTitle: TextView

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.gallery_item_view, this, true)
        mImage = findViewById(R.id.image_view)
        mTitle = findViewById(R.id.tv)
    }

    fun updateView(itemConfig: GalleryImageConfig) {
        Log.d("gal", "loading itemConfig: ${itemConfig.title}, ${itemConfig.imageUrl}")
        Glide.with(this)
            .load(itemConfig.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .fallback(R.drawable.placeholder)
            .into(mImage)
        mTitle.text = itemConfig.title
    }

}