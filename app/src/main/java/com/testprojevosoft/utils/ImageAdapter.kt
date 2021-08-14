package com.testprojevosoft.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testprojevosoft.R
import com.testprojevosoft.databinding.ImageItemBinding
import com.testprojevosoft.databinding.ProgressBarItemBinding

private const val VIEW_TYPE_PICTURE = 1
private  const val VIEW_TYPE_PROGRESS = 2

class ImageAdapter(private var images: MutableList<String?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OpenImageCallbacks {
        fun goToOpenImage(imageUrl: String?)
        fun deleteImage(image: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType) {
            VIEW_TYPE_PICTURE -> {
                val binding = ImageItemBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }
            else -> {
                val binding = ProgressBarItemBinding.inflate(inflater, parent, false)
                ProgressViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ImageViewHolder -> holder.bindImage(position)
        }
    }

    override fun getItemCount() = images.size

    override fun getItemViewType(position: Int): Int {
        return when(images[position] != null) {
            true -> VIEW_TYPE_PICTURE
            false -> VIEW_TYPE_PROGRESS
        }
    }

    // holder for image
    inner class ImageViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                (itemView.context as OpenImageCallbacks)
                    .goToOpenImage(images[absoluteAdapterPosition])
            }
        }

        fun bindImage(position: Int) {
            Glide.with(binding.image.context)
                .load(images[position])
                .placeholder(R.drawable.ic_image_placeholder)
                .centerCrop()
                .into(binding.image)
        }
    }

    // holder for progress bar
    inner class ProgressViewHolder(binding: ProgressBarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    // add null data to start progress bar
    fun addNullData() {
        images.add(null)
        notifyItemInserted(images.size - 1)
    }

    // remove null data to hide progress bar
    private fun removeNull() {
        images.removeAt(images.size - 1)
        notifyItemRemoved(images.size)
    }

    // add new images to adapter
    fun addImages(newImages: List<String>) {
        removeNull()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    // delete image from adapter
    fun deleteImage(image: String) {
        images.remove(image)
        notifyDataSetChanged()
    }
}