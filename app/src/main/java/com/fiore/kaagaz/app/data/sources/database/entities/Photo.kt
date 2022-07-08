package com.fiore.kaagaz.app.data.sources.database.entities

import androidx.room.*
import com.fiore.kaagaz.app.domain.model.Photo

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = Album::class,
            parentColumns = ["album_id"],
            childColumns = ["album_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("album_id")]
)
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "photo_id")
    val id: String,

    @ColumnInfo(name = "photo_name")
    val name: String,

    @ColumnInfo(name = "photo_uri")
    val photoUri: String,

    @ColumnInfo(name = "album_id")
    val albumId: String,

    @ColumnInfo(name = "time_captured")
    val timeCaptured: Long
) {
    fun toPhoto() = Photo(
        id = id,
        name = name,
        photoUri = photoUri,
        albumId = albumId,
        timeCaptured = timeCaptured
    )
}