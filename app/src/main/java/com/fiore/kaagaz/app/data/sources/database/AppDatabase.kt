package com.fiore.kaagaz.app.data.sources.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fiore.kaagaz.app.data.sources.database.daos.AlbumDao
import com.fiore.kaagaz.app.data.sources.database.daos.PhotosDao
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import com.fiore.kaagaz.app.utils.AppConstants.DATABASE_NAME

@Database(entities = [Album::class, Photo::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumDao
    abstract fun photosDao(): PhotosDao

    companion object {
        fun buildDatabase(context : Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}