package com.piny.kode_recipes_test.extensions

import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener

fun ImageSlider.onItemSelected(action: (Int) -> Unit) {
    this.setItemClickListener(object : ItemClickListener {
        override fun onItemSelected(position: Int) {
            action(position)
        }
    })
}