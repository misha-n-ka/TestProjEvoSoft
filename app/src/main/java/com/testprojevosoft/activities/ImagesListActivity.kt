package com.testprojevosoft.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.testprojevosoft.databinding.ActivityPicturesListBinding
import com.testprojevosoft.utils.ImageAdapter
import com.testprojevosoft.utils.InfiniteScrollListener
import com.testprojevosoft.viewModels.ImageListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImagesListActivity : AppCompatActivity(),
    InfiniteScrollListener.OnLoadMoreListener,
    ImageAdapter.OpenImageNavigator {

    private lateinit var mBinding: ActivityPicturesListBinding
    private lateinit var mInfiniteScrollListener: InfiniteScrollListener
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var openImageActivityLauncher: ActivityResultLauncher<Intent>

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

        openImageActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    deleteImage(result.data?.getStringExtra(EXTRA_DELETE_IMAGE)!!)
                }
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

    override fun deleteImage(image: String) {
        imageAdapter.deleteImage(image)
    }

    override fun goToOpenImage(imageUrl: String?) {

        val intent = Intent(applicationContext, OpenImageActivity::class.java).apply {
            putExtra(EXTRA_OPEN_IMAGE, imageUrl)
        }
        openImageActivityLauncher.launch(intent)
    }

    companion object {
        const val EXTRA_OPEN_IMAGE = "extraOpenImage"
        const val EXTRA_DELETE_IMAGE = "extraDeleteImage"
    }
}