package com.fiore.kaagaz.app.ui.gallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fiore.kaagaz.app.databinding.ListItemAlbumBinding
import com.fiore.kaagaz.app.domain.model.Album


class AlbumsAdapter(
    private val onAlbumClicked : (Album) -> Unit
) : ListAdapter<Album, AlbumsAdapter.AlbumViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
    }

    inner class AlbumViewHolder(
        private val binding: ListItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()
        }

        init {
            binding.albumContainer.setOnClickListener { binding.album?.let(onAlbumClicked) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlbumViewHolder(
            ListItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}