package com.interview.dunzo_gallery.network

import com.interview.dunzo_gallery.model.GalleryPhoto

object NetworkHelpers {

    val BASE_URL = "https://api.flickr.com/services/rest"

    fun getUrl(searchText: String, pageOffset: Int, count: Int): String {
        return "$BASE_URL?method=flickr.photos.search&api_key=062a6c0c49e4de1d78497d13a7dbb360" +
                "&text=${searchText}&format=json&nojsoncallback=1&per_page=${count}&page=${pageOffset}"
    }

    fun getImageUrl(galleryPhoto: GalleryPhoto): String {
        return "https://farm${galleryPhoto.farm}.staticflickr.com/${galleryPhoto.server}/" +
                "${galleryPhoto.id}_${galleryPhoto.secret}_m.jpg"
    }
}