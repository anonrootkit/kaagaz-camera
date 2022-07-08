package com.fiore.kaagaz.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val id : String,
    val name : String,
    val path : String
) : Parcelable