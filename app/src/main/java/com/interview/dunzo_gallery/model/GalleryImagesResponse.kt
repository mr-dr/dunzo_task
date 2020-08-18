package com.interview.dunzo_gallery.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryImagesResponse(
    @SerializedName("photos") val photos: GalleryData?,
    @SerializedName("stat") val stat: String?
) : Parcelable

@Parcelize
data class GalleryData (
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("perpage") val perpage: Int?,
    @SerializedName("total") val total: String?, // actually number is received
    @SerializedName("photo") val photo: List<GalleryPhoto>?
) : Parcelable

@Parcelize
data class GalleryPhoto (
    @SerializedName("id") val id: String?,
    @SerializedName("owner") val owner: String?,
    @SerializedName("secret") val secret: String?,
    @SerializedName("server") val server: String?,
    @SerializedName("farm") val farm: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("ispublic") val ispublic: Int?,
    @SerializedName("isfriend") val isfriend: Int?,
    @SerializedName("isfamily") val isfamily: Int?
): Parcelable