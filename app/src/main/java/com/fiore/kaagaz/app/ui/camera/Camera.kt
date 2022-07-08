package com.fiore.kaagaz.app.ui.camera

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fiore.kaagaz.app.R
import com.fiore.kaagaz.app.data.sources.database.entities.Photo
import com.fiore.kaagaz.app.databinding.FragmentCameraBinding
import com.fiore.kaagaz.app.ui.camera.adapters.CapturedImagesPreviewAdapter
import com.fiore.kaagaz.app.ui.viewmodels.GalleryViewModel
import com.fiore.kaagaz.app.ui.viewmodels.HomeViewModel
import com.fiore.kaagaz.app.utils.app.BaseHomeFragment
import com.fiore.kaagaz.app.utils.getUniquePhotoName
import com.fiore.kaagaz.app.utils.navigateUp
import com.fiore.kaagaz.app.utils.safeNavigate
import com.fiore.kaagaz.app.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class Camera : BaseHomeFragment(R.layout.fragment_camera) {

    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val galleryViewModel by viewModels<GalleryViewModel>()

    private lateinit var binding: FragmentCameraBinding
    private lateinit var imageCapture : ImageCapture
    private lateinit var previewPhotosAdapter : CapturedImagesPreviewAdapter

    private val newAlbumId by lazy { UUID.randomUUID().toString() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCameraBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        attachObservers()
    }

    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    galleryViewModel.photosList.collectLatest {
                        previewPhotosAdapter.submitList(it) {
                            binding.imagesPreviewList.smoothScrollToPosition(0)
                        }
                    }
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        toggleFullScreen(fullScreen = true)

        val permissionsGranted = homeViewModel.permissionsGranted.value
        if (!permissionsGranted) navigateUp().also { showToast(R.string.need_camera_permissions_msg, long = true) }
    }

    override fun onStop() {
        super.onStop()
        toggleFullScreen(fullScreen = false)
    }

    private fun initViews() {
        startCameraPreview()
        initAdapter()
        loadPreviewImages()

        binding.shutterButton.setOnClickListener { capturePhoto() }
    }

    private fun initAdapter() {
        previewPhotosAdapter = CapturedImagesPreviewAdapter { safeNavigate(CameraDirections.actionCameraToPreview(photo = it)) }
        binding.imagesPreviewList.adapter = previewPhotosAdapter
    }

    private fun loadPreviewImages() {
        galleryViewModel.getPhotosAsync(albumId = newAlbumId)
    }

    private fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val cameraPreview = Preview.Builder().build()
                .also { it.setSurfaceProvider(binding.cameraPreview.surfaceProvider) }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    cameraPreview,
                    imageCapture
                )
            } catch (e: Exception) {
                showToast("Error occurred : ${e.message}", long = true)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun capturePhoto() {
        val name = getUniquePhotoName()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Camera/$newAlbumId")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(this.javaClass.name, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savePhotoDetails(name = name, uri = output.savedUri)
                }
            })

    }

    private fun savePhotoDetails(name: String, uri: Uri?) {
        uri?.let {
            galleryViewModel.insertPhoto(
                photo = Photo(
                    name = name,
                    id = UUID.randomUUID().toString(),
                    photoUri = it.toString(),
                    albumId = newAlbumId,
                    timeCaptured = System.currentTimeMillis()
                )
            )
        }
    }
}