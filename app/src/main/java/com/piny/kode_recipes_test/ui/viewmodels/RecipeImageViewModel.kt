package com.piny.kode_recipes_test.ui.viewmodels

import androidx.lifecycle.*
import com.piny.kode_recipes_test.data.repository.ImageRepository
import com.piny.kode_recipes_test.utils.Result
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeImageViewModel(private val imageRepository: ImageRepository) :
    BaseViewModel<String?>() {

    fun downloadImage(imageUrl: String) {
        viewModelScope.launch {
            _state.value = Result.Loading()
            try {
                val uri = imageRepository.saveImage(imageUrl)

                _state.value = Result.Success(uri.encodedPath)
            } catch (e: Exception) {
                _state.value = Result.Error(e)
            } finally {
                _state.value = Result.Empty()
            }
        }
    }
}
