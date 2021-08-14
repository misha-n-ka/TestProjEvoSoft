package com.testprojevosoft.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.testprojevosoft.R
import com.testprojevosoft.Repository
import com.testprojevosoft.databinding.ActivityOpenPictureBinding
import kotlinx.coroutines.*

class OpenImageActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityOpenPictureBinding
    private var imageUrl: String? = ""
    private val mRepository = Repository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityOpenPictureBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        // get image url from intent
        imageUrl = intent.getStringExtra(ImagesListActivity.EXTRA_OPEN_IMAGE)
    }

    override fun onStart() {
        super.onStart()

        mBinding.image.visibility = View.INVISIBLE
        mBinding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { mRepository.getImage() }
            // loading image from url into imageview
            Glide.with(this@OpenImageActivity)
                .load(imageUrl)
                .into(mBinding.image)

            mBinding.image.visibility = View.VISIBLE
            mBinding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.activity_open_image, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // if clicked delete menu button
            R.id.deleteImage -> {
                // intent for result
                val data = Intent().apply {
                    putExtra(ImagesListActivity.EXTRA_DELETE_IMAGE, imageUrl)
                }
                setResult(Activity.RESULT_OK, data)
                // hide image view
                mBinding.image.visibility = View.INVISIBLE
                // appear progress bar
                mBinding.progressBar.visibility = View.VISIBLE
                //launching coroutine for deleting request
                lifecycleScope.launch(Dispatchers.Main) {
                    launch { delay(2000L) }.join()
                    onBackPressed()
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}