package com.fiore.kaagaz.app.domain.model

import android.os.Parcelable
import com.fiore.kaagaz.app.utils.AppConstants.PHOTO_PREVIEW_DATE_FORMAT
import com.fiore.kaagaz.app.utils.getFormattedDate
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id : String,
    val name : String,
    val photoUri : String,
    val albumId : String,
    val timeCaptured : Long
) : Parcelable {

    fun getDate() = timeCaptured.getFormattedDate(format = PHOTO_PREVIEW_DATE_FORMAT)
}