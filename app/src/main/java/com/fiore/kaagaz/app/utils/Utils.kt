package com.fiore.kaagaz.app.utils

import java.text.SimpleDateFormat
import java.util.*


fun getUniquePhotoName() : String {
    return String.format(
        AppConstants.FILENAME_FORMAT,
        SimpleDateFormat(AppConstants.FILENAME_SUFFIX_FORMAT, Locale.US).format(System.currentTimeMillis())
    )
}

fun Long.getFormattedDate(format : String) : String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}