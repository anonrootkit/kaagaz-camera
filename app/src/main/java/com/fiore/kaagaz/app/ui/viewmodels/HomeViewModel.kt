package com.fiore.kaagaz.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _permissionsGranted = MutableStateFlow<Boolean>(false)
    val permissionsGranted = _permissionsGranted.asStateFlow()

    private val _askForPermissions = MutableStateFlow<Boolean>(false)
    val askForPermissions = _askForPermissions.asStateFlow()

    fun updateAllPermissionsGranted(granted : Boolean) {
        _permissionsGranted.update { granted }
    }

    fun askForPermissions() {
        _askForPermissions.update { true }
    }

    fun resetAskForPermissions() {
        _askForPermissions.update { false }
    }
}