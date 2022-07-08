package com.fiore.kaagaz.app.utils.app

import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

open class BaseHomeFragment(@LayoutRes layoutId : Int) : Fragment(layoutId) {

    fun toggleFullScreen(fullScreen : Boolean) {
        val windowInsetsController = view?.let { ViewCompat.getWindowInsetsController(it) } ?: return

        if (fullScreen) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }else{
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

}