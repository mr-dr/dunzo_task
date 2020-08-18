package com.interview.dunzo_gallery.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.interview.dunzo_gallery.GalleryListener
import com.interview.dunzo_gallery.MainRepository
import com.interview.dunzo_gallery.model.GalleryImageConfig
import com.interview.dunzo_gallery.model.GalleryImagesResponse
import com.interview.dunzo_gallery.model.GalleryPhoto
import com.interview.dunzo_gallery.network.NetworkHelpers
import com.interview.dunzo_gallery.ui.view.MainView
import com.interview.dunzo_gallery.util.Constants

class MainViewModel(val view: MainView, context: Context) {

    // for pagination
    var totalPages = 0
    var totalImages = 0
    var fetchingResponse = false

    val liveData = MutableLiveData<List<List<GalleryImageConfig>>>()
    var pageIndex = 1
    val repo = MainRepository(context)
    lateinit var searchText: String
    fun getImagesLiveData(): LiveData<List<List<GalleryImageConfig>>> {
        return liveData
    }

    fun fetchFirstPage(searchText: String) {
        pageIndex = 1
        this.searchText = searchText
        liveData.value = ArrayList()
        fetchResults()
    }

    fun fetchNextPage() {
        fetchResults()
    }

    private fun fetchResults() {
        fetchingResponse = true
        view.showProgressDialog()
        repo.fetchResults(searchText, pageIndex, Constants.MAX_IMAGES_PER_PAGE, object : GalleryListener {

            override fun onSuccess(response: GalleryImagesResponse) {
                view.hideProgressDialog()
                fetchingResponse = false
                val photosVMConfigs = getPhotosVMConfig(response?.photos?.photo)
                    ?: return
                totalPages = response.photos?.pages ?: 0
                try { // try has to be used due to incorrect API response
                    totalImages = response.photos?.total?.toInt() ?: 0
                } catch (e: Exception) {
                    totalImages = 0
                }
                val list = ArrayList<List<GalleryImageConfig>>()
                list.addAll(liveData.value as List)
                list.addAll(photosVMConfigs)
                liveData.value = list
            }

            override fun onError(error: VolleyError) {
                view.hideProgressDialog()
                fetchingResponse = false
                view.showToast(Constants.ERROR_MSG)
            }

        })
        pageIndex++
    }

    private fun getPhotosVMConfig(photos: List<GalleryPhoto>?): List<List<GalleryImageConfig>>? {
        if (photos == null) return null
        val rowConfigs = ArrayList<List<GalleryImageConfig>>()
        var i = 0
        var currentRow = ArrayList<GalleryImageConfig>()
        while (i < photos.size) {
            if (i > 0 && i % Constants.MAX_IMAGES_PER_ROW == 0) {
                rowConfigs.add(currentRow)
                currentRow = ArrayList()
            }
            currentRow.add(getPhotosVM(photos[i++]))
        }
        if (currentRow.size > 0) rowConfigs.add(currentRow)
        return rowConfigs
    }

    private fun getPhotosVM(galleryPhoto: GalleryPhoto): GalleryImageConfig {
        return GalleryImageConfig(
            NetworkHelpers.getImageUrl(galleryPhoto),
            galleryPhoto.title ?: ""
        )
    }

}
