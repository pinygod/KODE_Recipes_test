package com.piny.kode_recipes_test.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showMessage(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}