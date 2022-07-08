package com.fiore.kaagaz.app.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun loadImageFromContentUriToImageView(view : ImageView, uri : String?) {
    uri?.let { photoContentUri ->
        Glide.with(view).load(photoContentUri).into(view)
    }
}