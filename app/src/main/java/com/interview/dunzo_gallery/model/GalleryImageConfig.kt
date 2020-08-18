package com.interview.dunzo_gallery.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryImageConfig(
    val imageUrl: String,
    val title: String
) : Parcelable
