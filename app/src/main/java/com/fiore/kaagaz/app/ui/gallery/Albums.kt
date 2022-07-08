package com.fiore.kaagaz.app.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fiore.kaagaz.app.R
import com.fiore.kaagaz.app.databinding.FragmentAlbumsBinding
import com.fiore.kaagaz.app.ui.gallery.adapters.AlbumsAdapter
import com.fiore.kaagaz.app.ui.viewmodels.GalleryViewModel
import com.fiore.kaagaz.app.ui.viewmodels.HomeViewModel
import com.fiore.kaagaz.app.utils.app.BaseHomeFragment
import com.fiore.kaagaz.app.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Albums : BaseHomeFragment(R.layout.fragment_albums) {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val galleryViewModel by viewModels<GalleryViewModel>()

    private lateinit var binding : FragmentAlbumsBinding
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAlbumsBinding.bind(view)
        binding.lifecycleOwner = this

        initToolbar()
        initViews()
        attachObservers()
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            upButton.visibility = View.GONE
            title.text = getString(R.string.albums)
        }
    }

    private fun initViews() {
        binding.cameraButton.setOnClickListener {
            val permissionsGranted = homeViewModel.permissionsGranted.value
            if (permissionsGranted) safeNavigate(AlbumsDirections.actionAlbumsToCamera())
            else homeViewModel.askForPermissions()
        }

        binding.albumList.apply {
            adapter = AlbumsAdapter {
                safeNavigate(AlbumsDirections.actionAlbumsToPhotos(album = it))
            }.also { albumsAdapter = it }
        }
    }

    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    galleryViewModel.albumsList.collectLatest {
                        albumsAdapter.submitList(it)
                        binding.emptyAlbumListLabel.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

}