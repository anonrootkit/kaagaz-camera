package com.fiore.kaagaz.app.ui.gallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fiore.kaagaz.app.databinding.ListItemPhotoBinding
import com.fiore.kaagaz.app.databinding.ListItemPhotoPreviewBinding
import com.fiore.kaagaz.app.domain.model.Photo

class PhotosAdapter(
    private val onPreviewClicked : (Photo) -> Unit
) : ListAdapter<Photo, PhotosAdapter.ImagePreviewViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }

    inner class ImagePreviewViewHolder(
        private val binding: ListItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }

        init {
            binding.photoContainer.setOnClickListener { binding.photo?.let(onPreviewClicked) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImagePreviewViewHolder(
            ListItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImagePreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}