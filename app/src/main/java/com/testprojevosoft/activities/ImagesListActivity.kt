package com.testprojevosoft.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.testprojevosoft.R
import com.testprojevosoft.databinding.ActivityPicturesListBinding
import com.testprojevosoft.utils.ImageAdapter
import com.testprojevosoft.utils.InfiniteScrollListener
import com.testprojevosoft.viewModels.ImageListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImagesListActivity : AppCompatActivity(),
    InfiniteScrollListener.OnLoadMoreListener,
    ImageAdapter.OpenImageCallbacks {

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

        // init layout manager
        val manager = LinearLayoutManager(this)
        // init infinite scroll listener
        mInfiniteScrollListener = InfiniteScrollListener(10, manager, this)
        // reset infinite scroll listener
        mInfiniteScrollListener.setLoaded()

        // init recyclerview
        mBinding.imagesRecyclerView.apply {
            layoutManager = manager
            addOnScrollListener(mInfiniteScrollListener)
            imageAdapter = ImageAdapter(images)
            adapter = imageAdapter
        }

        // init contract for deleting image result
        openImageActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    deleteImage(result.data?.getStringExtra(EXTRA_DELETE_IMAGE)!!)
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // loading first 10 images
        onLoadMore(10)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflate toolbar menu
        menuInflater.inflate(R.menu.activity_pictures_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // if clicked on logout menu button
            R.id.logOut -> {
                startActivity(Intent(applicationContext, AuthorizationMainActivity::class.java))
                finish()
                true
            }
            else -> false
        }
    }

    override fun onLoadMore(numLoadItems: Int) {
        // launch coroutine for loading new images in pictures list
        lifecycleScope.launch(Dispatchers.Main) {
            // set null data for starting loading
            imageAdapter.addNullData()
            //launch async coroutine
            val newImages = async { imageListViewModel.getNextImages(numLoadItems) }
            //awaiting result from launched coroutine and setting new images to list
            imageAdapter.addImages(newImages.await())
            //reset infinite scroll listener
            mInfiniteScrollListener.setLoaded()
        }
    }

    override fun deleteImage(image: String) {
        imageAdapter.deleteImage(image)
    }

    override fun goToOpenImage(imageUrl: String?) {
        // intent with image url for OpenImageActivity
        val intent = Intent(applicationContext, OpenImageActivity::class.java).apply {
            putExtra(EXTRA_OPEN_IMAGE, imageUrl)
        }
        // launching activity for result
        openImageActivityLauncher.launch(intent)
    }

    companion object {
        const val EXTRA_OPEN_IMAGE = "extraOpenImage"
        const val EXTRA_DELETE_IMAGE = "extraDeleteImage"
    }
}