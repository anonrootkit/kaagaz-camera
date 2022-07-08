package com.fiore.kaagaz.app.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.fiore.kaagaz.app.data.sources.database.entities.Album
import com.fiore.kaagaz.app.data.sources.database.entities.Photo

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun NavController.safeNavigate(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.run { navigate(directions) }
}

fun Fragment.safeNavigate(directions : NavDirections) {
    findNavController().safeNavigate(directions)
}

fun List<String>.allPermissionsGranted(context: Context) : Boolean {
    return all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
}

fun Array<String>.allPermissionsGranted(context: Context) : Boolean {
    return all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
}

fun Set<String>.allPermissionsGranted(context: Context) : Boolean {
    return all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
}

fun Fragment.showToast(msg: String, long: Boolean = false) {
    Toast.makeText(requireContext(), msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun Fragment.showToast(@StringRes msg: Int, long: Boolean = false) {
    Toast.makeText(requireContext(), msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun Activity.showToast(msg: String, long: Boolean = false) {
    Toast.makeText(this, msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun Activity.showToast(@StringRes msg: Int, long: Boolean = false) {
    Toast.makeText(this, msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}

fun List<Photo>.toPhotosList() : List<com.fiore.kaagaz.app.domain.model.Photo> {
    return map { it.toPhoto() }
}

fun List<Album>.toAlbumList() : List<com.fiore.kaagaz.app.domain.model.Album> {
    return map { it.toAlbum() }
}