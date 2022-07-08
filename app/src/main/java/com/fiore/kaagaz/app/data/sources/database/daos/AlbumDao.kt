package com.fiore.kaagaz.app.data.sources.database.daos

import androidx.room.*
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: Album)

    @Query("SELECT * FROM albums")
    fun getAlbumsAsync(): Flow<List<Album>>

    @Query("SELECT * FROM albums WHERE album_id=:albumId")
    suspend fun getAlbumById(albumId : String) : Album?

    @Delete
    suspend fun deleteAlbum(album: Album)
}