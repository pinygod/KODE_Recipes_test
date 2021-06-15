package com.piny.kode_recipes_test.ui.viewmodels

import androidx.core.text.HtmlCompat
import androidx.lifecycle.*
import com.denzcoskun.imageslider.models.SlideModel
import com.piny.kode_recipes_test.data.RecipeDetails
import com.piny.kode_recipes_test.data.repository.RecipesRepository
import com.piny.kode_recipes_test.utils.Result
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class RecipeDetailsViewModel(private val recipesRepository: RecipesRepository) :
    BaseViewModel<RecipeDetails>() {

    fun setRecipe(recipeUuid: UUID) {
        viewModelScope.launch {
            _state.value = Result.Loading()
            try {
                val data = recipesRepository.getRecipe(recipeUuid)
                    ?: throw Exception("Error while loading recipe")

                data.instructions =
                    HtmlCompat.fromHtml(data.instructions, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString()

                _state.value = Result.Success(data)
            } catch (e: Exception) {
                _state.value = Result.Error(e)
            }
        }
    }

    fun getRecipeSlides(): List<SlideModel>? = _state.value.data?.images?.map { SlideModel(it) }
}