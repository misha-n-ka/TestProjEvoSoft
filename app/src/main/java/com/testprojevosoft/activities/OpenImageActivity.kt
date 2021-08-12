package com.testprojevosoft.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.testprojevosoft.R
import com.testprojevosoft.databinding.ActivityOpenPictureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OpenImageActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityOpenPictureBinding
    private var imageUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityOpenPictureBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        // get image url from intent
        intent.getStringExtra(ImagesListActivity.EXTRA_OPEN_IMAGE).also { imageUrl = it }
    }

    override fun onStart() {
        super.onStart()

        // loading image from url into imageview
        Glide.with(this)
            .load(imageUrl)
            .into(mBinding.image)
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
                GlobalScope.launch(Dispatchers.Main) {
                    launch { delay(2000L) }.join()
                    onBackPressed()
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}