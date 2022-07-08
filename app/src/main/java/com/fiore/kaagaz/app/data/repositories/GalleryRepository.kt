package com.fiore.kaagaz.app.data.repositories

import com.fiore.kaagaz.app.data.sources.database.daos.AlbumDao
import com.fiore.kaagaz.app.data.sources.database.daos.PhotosDao
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val albumDao: AlbumDao,
    private val photosDao: PhotosDao
) {
    suspend fun insertAlbum(album: Album) {
        withContext(Dispatchers.IO) {
            albumDao.insertAlbum(album)
        }
    }

    fun getAlbumsAsync(): Flow<List<Album>> {
        return albumDao.getAlbumsAsync()
    }

    suspend fun deleteAlbum(album: Album) {
        withContext(Dispatchers.IO) {
            albumDao.deleteAlbum(album)
        }
    }

    suspend fun insertPhoto(photo: Photo) {
        withContext(Dispatchers.IO) {
            photosDao.insertPhoto(photo)
        }
    }

    fun getPhotosAsync(albumId: String): Flow<List<Photo>> {
        return photosDao.getPhotosAsync(albumId)
    }

    suspend fun deletePhoto(photo: Photo) {
        withContext(Dispatchers.IO) {
            photosDao.deletePhoto(photo)
        }
    }

}