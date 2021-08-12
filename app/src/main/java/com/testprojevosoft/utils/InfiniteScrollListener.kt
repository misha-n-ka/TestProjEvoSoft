package com.testprojevosoft.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(private val numLoadItems: Int,
    private val layoutManager: LinearLayoutManager,
    private val listener: OnLoadMoreListener
) :
    RecyclerView.OnScrollListener() {

    interface OnLoadMoreListener {
        fun onLoadMore(numLoadItems: Int)
    }

    private var isLoading = false
    private var pauseListening = false
    private var END_OF_FEED_ADDED = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dx == 0 && dy == 0) {
            return
        }
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading
            && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD
            && totalItemCount != 0
            && !END_OF_FEED_ADDED
            && !pauseListening
        ) {
            // start loading new numLoadItems images
            listener.onLoadMore(numLoadItems)
            isLoading = true
        }
    }

    //reset infinite scroll listener
    fun setLoaded() {
        isLoading = false
    }

    // for later usage
    // flag for end of requesting data
    fun addEndOfRequests() {
        this.END_OF_FEED_ADDED = true
    }

    //for later usage
    fun pauseScrollListener(pauseListening: Boolean) {
        this.pauseListening = pauseListening
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 2
    }
}