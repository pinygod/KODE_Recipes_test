package com.piny.kode_recipes_test.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.onItemSelected(action: (Any) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (parent != null) {
                action(parent.getItemAtPosition(position))
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

    }
}