package com.testprojevosoft.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.testprojevosoft.R
import com.testprojevosoft.databinding.ActivityMainBinding
import com.testprojevosoft.databinding.ActivityOpenPictureBinding

class OpenImageActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityOpenPictureBinding
    private var imageUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityOpenPictureBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        intent.getStringExtra(ImagesListActivity.EXTRA_OPEN_IMAGE).also { imageUrl = it }
    }

    override fun onStart() {
        super.onStart()

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
            R.id.deleteImage -> {
                val data = Intent().apply {
                    putExtra(ImagesListActivity.EXTRA_DELETE_IMAGE, imageUrl)
                }
                setResult(Activity.RESULT_OK, data)
                onBackPressed()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}