package com.piny.kode_recipes_test.extensions

import android.widget.ImageView
import com.piny.kode_recipes_test.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageFromUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Picasso.get()
            .load(url)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .into(this)
    }
}