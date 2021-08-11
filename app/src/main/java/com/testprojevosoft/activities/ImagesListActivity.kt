package com.testprojevosoft.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.testprojevosoft.data.Database
import com.testprojevosoft.databinding.ActivityMainBinding
import com.testprojevosoft.databinding.ActivityPicturesListBinding
import com.testprojevosoft.utils.ImageAdapter
import com.testprojevosoft.utils.InfiniteScrollListener
import com.testprojevosoft.viewModels.ImageListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImagesListActivity : AppCompatActivity(), InfiniteScrollListener.OnLoadMoreListener {

    private lateinit var mBinding: ActivityPicturesListBinding
    private lateinit var mInfiniteScrollListener: InfiniteScrollListener
    private lateinit var imageAdapter: ImageAdapter

    private var images: MutableList<String?> = mutableListOf()
    private val imageListViewModel: ImageListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityPicturesListBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        val manager = LinearLayoutManager(this)
        mInfiniteScrollListener = InfiniteScrollListener(10, manager, this)
        mInfiniteScrollListener.setLoaded()

        mBinding.imagesRecyclerView.apply {
            layoutManager = manager
            addOnScrollListener(mInfiniteScrollListener)
            imageAdapter = ImageAdapter(images)
            adapter = imageAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        onLoadMore(10)
    }

    override fun onLoadMore(numLoadItems: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            imageAdapter.addNullData()
            val newImages = async { imageListViewModel.getNextImages(numLoadItems) }
            imageAdapter.addImages(newImages.await())
            mInfiniteScrollListener.setLoaded()
        }
    }
}