package com.interview.dunzo_gallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interview.dunzo_gallery.R
import com.interview.dunzo_gallery.listener.PaginationScrollListener
import com.interview.dunzo_gallery.model.GalleryImageConfig
import com.interview.dunzo_gallery.ui.view.MainView
import com.interview.dunzo_gallery.vm.MainViewModel
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import com.interview.dunzo_gallery.util.Constants


class MainActivity : AppCompatActivity(), View.OnClickListener, MainView {

    lateinit var mViewModel: MainViewModel
    lateinit var mSearchEditText: EditText
    lateinit var mLastSearchTv: TextView
    lateinit var mSearchBtn: View
    lateinit var mGalleryRV: RecyclerView
    lateinit var mAdapter: GalleryAdapter
    lateinit var mInfoTv: TextView
    lateinit var mProgressBar: View

    private var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = MainViewModel(this, applicationContext)
        init()
    }

    private fun init() {
        initViews()
        initUIListeners()
        initDataListeners()
    }

    private fun initViews() {
        mSearchEditText = findViewById(R.id.search_tv)
        mLastSearchTv = findViewById(R.id.last_searched_tv)
        mSearchBtn = findViewById(R.id.search_btn)
        mGalleryRV = findViewById(R.id.gallery_rv)
        mInfoTv = findViewById(R.id.info_tv)
        mProgressBar = findViewById(R.id.progress_bar)
        val llm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mGalleryRV.setLayoutManager(llm)
        mAdapter = GalleryAdapter(this)
        mGalleryRV.adapter = mAdapter
        val scrollListener = object : PaginationScrollListener(llm) {
            override fun loadMoreItems() {
                mViewModel.fetchNextPage()
            }

            override fun totalPageCount(): Int {
                return mViewModel.totalPages
            }

            override fun isLastPage(): Boolean {
                return mViewModel.pageIndex == mViewModel.totalPages
            }

            override fun isLoading(): Boolean {
                return mViewModel.fetchingResponse
            }
        }
        mGalleryRV.setOnScrollListener(scrollListener)
    }

    private fun initUIListeners() {
        mSearchBtn.setOnClickListener(this)
    }

    private fun initDataListeners() {
        val imagesLiveData: LiveData<List<List<GalleryImageConfig>>> =
            mViewModel.getImagesLiveData()
        imagesLiveData.observeForever {
            val imagesShown = Constants.MAX_IMAGES_PER_PAGE * mViewModel.pageIndex
            mLastSearchTv.text = getString(R.string.showing_results_for, mViewModel.searchText)
            if (it.size > 0) {
                setVisibilitiesForResultsPresent(true)
                mAdapter.setData(it)
            } else {
                mInfoTv.text = getString(R.string.no_results)
                setVisibilitiesForResultsPresent(false)
            }
        }
    }

    private fun setVisibilitiesForResultsPresent(resultsPresent: Boolean) {
        if(resultsPresent) {
            mInfoTv.visibility = View.GONE
            mLastSearchTv.visibility = View.VISIBLE
            mGalleryRV.visibility = View.VISIBLE
        } else {
            mInfoTv.visibility = View.VISIBLE
            mLastSearchTv.visibility = View.GONE
            mGalleryRV.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        if (v == null) return
        when (v.id) {
            R.id.search_btn -> {
                val searchText = mSearchEditText.text.toString()
                hideKeyboard()
                if (!TextUtils.isEmpty(searchText)) { // TODO add progress bars
                    mViewModel.fetchFirstPage(searchText)
                } else {
                    showToast(getString(R.string.no_search_keyword))
                }
            }
        }
    }

    // below functions can be moved to base activity in case of more activities

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showProgressDialog() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressDialog() {
        mProgressBar.visibility = View.GONE
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm!!.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
