package com.fiore.kaagaz.app.data.sources.database.daos

import androidx.room.*
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photo: Photo)

    @Query("SELECT * FROM photos WHERE album_id=:albumId ORDER BY time_captured DESC")
    fun getPhotosAsync(albumId : String): Flow<List<Photo>>

    @Query("SELECT * FROM photos WHERE photo_id=:photoId")
    fun getPhotoById(photoId : String): Photo?

    @Delete
    suspend fun deletePhoto(photo: Photo)
}