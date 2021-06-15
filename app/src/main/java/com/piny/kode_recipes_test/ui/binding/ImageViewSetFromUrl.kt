package com.piny.kode_recipes_test.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.piny.kode_recipes_test.extensions.loadImageFromUrl

@BindingAdapter(value = ["imageUrl"])
fun ImageView.setImageUrl(url: String?) {
    loadImageFromUrl(url)
}