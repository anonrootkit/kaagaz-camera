package com.fiore.kaagaz.app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiore.kaagaz.app.data.sources.database.AppDatabase
import com.fiore.kaagaz.app.data.sources.database.daos.AlbumDao
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AlbumInsertionTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun initDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        albumDao = appDatabase.albumsDao()
    }

    @Test
    fun checkAlbumInsertionWorkingFineTest() = runBlocking {
        val albumId = UUID.randomUUID().toString()
        val albumName = "Test"

        val album = Album(id = albumId, name = albumName, path = "")
        albumDao.insertAlbum(album)

        val albumById =  albumDao.getAlbumById(albumId = albumId)
        assert(albumById?.name == albumName)
    }

    @After
    fun nukeDatabase() {
        appDatabase.close()
    }


}