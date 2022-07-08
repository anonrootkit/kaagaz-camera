package com.fiore.kaagaz.app.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fiore.kaagaz.app.R
import com.fiore.kaagaz.app.ui.viewmodels.HomeViewModel
import com.fiore.kaagaz.app.utils.AppConstants.REQUIRED_PERMISSIONS
import com.fiore.kaagaz.app.utils.allPermissionsGranted
import com.fiore.kaagaz.app.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.keys.allPermissionsGranted(this)) homeViewModel.updateAllPermissionsGranted(granted = true)
            else showToast(R.string.need_camera_permissions_msg, long = true)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        attachObservers()
    }

    override fun onStart() {
        super.onStart()

        homeViewModel.updateAllPermissionsGranted(
            granted = REQUIRED_PERMISSIONS.allPermissionsGranted(this)
        )
    }

    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    homeViewModel.askForPermissions.collectLatest { needPermissions ->
                        if (needPermissions) askForPermissions()
                        homeViewModel.resetAskForPermissions()
                    }
                }

            }
        }
    }

    private fun askForPermissions() {
        permissionLauncher.launch(REQUIRED_PERMISSIONS)
    }
}