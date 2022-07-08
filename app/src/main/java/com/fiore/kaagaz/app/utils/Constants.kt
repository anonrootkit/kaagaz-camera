package com.fiore.kaagaz.app.utils

import android.Manifest
import android.os.Build


object AppConstants {
    const val FILENAME_FORMAT = "Photo %s"
    const val FILENAME_SUFFIX_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    const val PHOTO_PREVIEW_DATE_FORMAT = "dd MMM, yyyy"

    const val DATABASE_NAME = "camerax_database"

    val REQUIRED_PERMISSIONS =
        mutableListOf (
            Manifest.permission.CAMERA,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
}