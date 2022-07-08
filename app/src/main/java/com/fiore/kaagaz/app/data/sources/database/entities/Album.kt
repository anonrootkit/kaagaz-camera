package com.fiore.kaagaz.app.data.sources.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fiore.kaagaz.app.domain.model.Album

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id : String,

    @ColumnInfo(name = "album_name")
    val name : String,

    @ColumnInfo(name = "album_path")
    val path : String
) {
    fun toAlbum() = Album(
        id = id,
        name = name,
        path = path
    )
}