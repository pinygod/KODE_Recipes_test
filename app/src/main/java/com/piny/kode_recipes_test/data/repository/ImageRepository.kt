package com.piny.kode_recipes_test.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ImageRepository(private val context: Context) {
    suspend fun saveImage(imageUrl: String) = withContext(Dispatchers.IO) {
        val fileName = "RecipeImage_${System.currentTimeMillis()}.jpg"

        val stream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            createQStream(fileName)
        } else {
            createStream(fileName)
        }

        val bitmap = Picasso.get().load(imageUrl).get()

        saveFile(stream.second, bitmap)
        stream.first
    }

    private fun saveFile(stream: OutputStream, bitmap: Bitmap) = with(stream) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
        flush()
        close()
    }

    @Suppress("DEPRECATION")
    private fun createStream(fileName: String): Pair<Uri, FileOutputStream> {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(dir, fileName)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        return Pair(uri, file.outputStream())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createQStream(fileName: String) = with(context.contentResolver) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val stream = uri?.let { openOutputStream(it) }
        if (uri != null && stream != null) {
            Pair(uri, stream)
        } else {
            throw Exception("Something went wrong...")
        }
    }

}