package com.fiore.kaagaz.app.ui.gallery

import android.os.Bundle
import android.view.View
import com.fiore.kaagaz.app.R
import com.fiore.kaagaz.app.databinding.FragmentPreviewBinding
import com.fiore.kaagaz.app.domain.model.Photo
import com.fiore.kaagaz.app.utils.app.BaseHomeFragment
import com.fiore.kaagaz.app.utils.navigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Preview : BaseHomeFragment(R.layout.fragment_preview) {
    private lateinit var binding : FragmentPreviewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = PreviewArgs.fromBundle(requireArguments()).photo

        binding = FragmentPreviewBinding.bind(view)
        binding.lifecycleOwner = this
        binding.photo = photo


        initToolbar(photo)
    }

    private fun initToolbar(photo: Photo) {
        binding.toolbar.apply {

            upButton.apply {
                visibility = View.VISIBLE
                setOnClickListener { navigateUp() }
            }

            date = photo.getDate()
        }
    }
}