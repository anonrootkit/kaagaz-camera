package com.fiore.kaagaz.app.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fiore.kaagaz.app.R
import com.fiore.kaagaz.app.databinding.FragmentPhotosBinding
import com.fiore.kaagaz.app.ui.gallery.adapters.PhotosAdapter
import com.fiore.kaagaz.app.ui.viewmodels.GalleryViewModel
import com.fiore.kaagaz.app.ui.viewmodels.HomeViewModel
import com.fiore.kaagaz.app.utils.app.BaseHomeFragment
import com.fiore.kaagaz.app.utils.navigateUp
import com.fiore.kaagaz.app.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Photos : BaseHomeFragment(R.layout.fragment_photos) {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val galleryViewModel by viewModels<GalleryViewModel>()

    private lateinit var binding : FragmentPhotosBinding
    private lateinit var photosAdapter: PhotosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val album = PhotosArgs.fromBundle(requireArguments()).album

        binding = FragmentPhotosBinding.bind(view)
        binding.lifecycleOwner = this

        initToolbar()
        initViews()
        attachObservers()

        galleryViewModel.getPhotosAsync(albumId = album.id)
    }

    private fun initToolbar() {
        binding.toolbar.apply {

            upButton.apply {
                visibility = View.VISIBLE
                setOnClickListener { navigateUp() }
            }

            title.text = getString(R.string.photos)
        }
    }

    private fun initViews() {
        binding.cameraButton.setOnClickListener {
            val permissionsGranted = homeViewModel.permissionsGranted.value
            if (permissionsGranted) safeNavigate(PhotosDirections.actionPhotosToCamera())
            else homeViewModel.askForPermissions()
        }

        binding.photosList.apply {
            adapter = PhotosAdapter {
                safeNavigate(PhotosDirections.actionPhotosToPreview(photo = it))
            }.also { photosAdapter = it }
        }
    }

    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    galleryViewModel.photosList.collectLatest {
                        photosAdapter.submitList(it)
                        binding.emptyAlbumLabel.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }


}