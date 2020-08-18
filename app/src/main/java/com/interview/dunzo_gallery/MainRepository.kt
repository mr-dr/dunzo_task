package com.interview.dunzo_gallery

import android.util.Log
import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.interview.dunzo_gallery.model.GalleryImagesResponse
import com.interview.dunzo_gallery.network.NetworkHelpers

class MainRepository(context: Context) {

    val gson = Gson()
    val queue = Volley.newRequestQueue(context) // TODO move context outside repo

    fun fetchResults(searchText: String, pageOffset: Int, count: Int, listener: GalleryListener) {
        val url = NetworkHelpers.getUrl(searchText, pageOffset, count)
        Log.d("gal", "fetching page: ${searchText}, ${pageOffset}, ${count}")
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val galleryResponse = gson.fromJson(response, GalleryImagesResponse::class.java)
                listener.onSuccess(galleryResponse)
            },
            Response.ErrorListener {
                listener.onError(it)
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}

interface GalleryListener {
    fun onSuccess(response: GalleryImagesResponse)
    fun onError(error: VolleyError)
}
