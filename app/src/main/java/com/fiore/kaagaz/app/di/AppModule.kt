package com.fiore.kaagaz.app.di

import android.content.Context
import com.fiore.kaagaz.app.data.sources.database.AppDatabase
import com.fiore.kaagaz.app.data.sources.database.daos.AlbumDao
import com.fiore.kaagaz.app.data.sources.database.daos.PhotosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase) : AlbumDao {
        return appDatabase.albumsDao()
    }

    @Provides
    fun providePhotosDao(appDatabase: AppDatabase) : PhotosDao {
        return appDatabase.photosDao()
    }
}