package com.fiore.kaagaz.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiore.kaagaz.app.data.repositories.GalleryRepository
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import com.fiore.kaagaz.app.utils.toAlbumList
import com.fiore.kaagaz.app.utils.toPhotosList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    val albumsList = galleryRepository.getAlbumsAsync().map { it.toAlbumList() }

    private val _photosList = MutableStateFlow<List<com.fiore.kaagaz.app.domain.model.Photo>>(emptyList())
    val photosList = _photosList.asStateFlow()

    fun deleteAlbum(album: Album) {
        viewModelScope.launch { galleryRepository.deleteAlbum(album) }
    }

    fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            galleryRepository.insertAlbum(album = Album(id = photo.albumId, name = photo.albumId, path = ""))
            galleryRepository.insertPhoto(photo)
        }
    }

    fun getPhotosAsync(albumId: String) {
        viewModelScope.launch {
            galleryRepository.getPhotosAsync(albumId).collectLatest { photoEntities ->
                _photosList.update { photoEntities.toPhotosList() }
            }
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch { galleryRepository.deletePhoto(photo) }
    }
}