package com.fiore.kaagaz.app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiore.kaagaz.app.data.sources.database.AppDatabase
import com.fiore.kaagaz.app.data.sources.database.daos.AlbumDao
import com.fiore.kaagaz.app.data.sources.database.daos.PhotosDao
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class PhotoInsertionTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var photosDao: PhotosDao
    private lateinit var albumDao: AlbumDao

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        photosDao = appDatabase.photosDao()
        albumDao = appDatabase.albumsDao()
    }

    @Test
    fun checkPhotoInsertionWorkingFineTest() = runBlocking {
        val albumId = UUID.randomUUID().toString()
        val albumName = "Test"

        val album = Album(id = albumId, name = albumName, path = "")
        albumDao.insertAlbum(album)

        val photoId = UUID.randomUUID().toString()
        val photo = Photo(id = photoId, albumId = albumId, name = "", photoUri = "", timeCaptured = -1L)

        photosDao.insertPhoto(photo)
        val photoById = photosDao.getPhotoById(photoId = photoId)

        assert(photoId == photoById?.id)
    }

    @After
    fun nukeDatabase() {
        appDatabase.close()
    }

}