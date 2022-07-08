package com.fiore.kaagaz.app.ui.camera.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fiore.kaagaz.app.databinding.ListItemPhotoPreviewBinding
import com.fiore.kaagaz.app.domain.model.Photo

class CapturedImagesPreviewAdapter(
    private val onPreviewClicked : (Photo) -> Unit
) :
    ListAdapter<Photo, CapturedImagesPreviewAdapter.ImagePreviewViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }

    inner class ImagePreviewViewHolder(
        private val binding: ListItemPhotoPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }

        init {
            binding.previewContainer.setOnClickListener { binding.photo?.let(onPreviewClicked) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImagePreviewViewHolder(
            ListItemPhotoPreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImagePreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}