package com.piny.kode_recipes_test.ui.viewmodels

import androidx.lifecycle.*
import com.piny.kode_recipes_test.data.Recipe
import com.piny.kode_recipes_test.data.repository.RecipesRepository
import com.piny.kode_recipes_test.utils.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipesListViewModel(private val recipesRepository: RecipesRepository) :
    BaseViewModel<List<Recipe>>() {

    private val _sortOrder = MutableLiveData("")
    private val _searchQuery = MutableLiveData("")

    //private lateinit var _recipesList: List<Recipe> // use without database

    init {
        viewModelScope.launch {
            combine(_searchQuery.asFlow(), _sortOrder.asFlow()) { query, order ->
                Pair(query, order)
            }.flatMapLatest { (query, order) -> //on every change of searchQuery or sortOrder this will create a new request to repo
                _state.value = Result.Loading()
                recipesRepository.getAllRecipes(
                    query,
                    order
                )
            }
                .catch { exception ->
                    _state.value = Result.Error(exception)
                }
                .collect { recipesList ->
                    _state.value = Result.Success(recipesList)
                }
        }

        /*viewModelScope.launch { // use without database
            _recipesList = recipesRepository.getAllRecipes()

            combine(_searchQuery.asFlow(), _sortOrder.asFlow()) { query, order ->
                Pair(query, order)
            }.collectLatest { (query, order) ->
                _state.value = Result.Loading()

                var processedList = sortRecipesByOrder(_recipesList, order)
                processedList = getRecipesByQuery(processedList, query)
                _state.value = Result.Success(processedList)
            }
        }*/
    }

    fun onSortChange(itemAtPosition: Any) {
        val item = itemAtPosition as String
        if (_sortOrder.value != item)
            _sortOrder.value = item
    }

    fun onQueryChanged(newQuery: CharSequence) {
        if (_searchQuery.value != newQuery)
            _searchQuery.value = newQuery.toString()
    }

    /*private fun sortRecipesByOrder(
        recipesList: List<Recipe>,
        sortOrder: String
    ): List<Recipe> { // use without database
        return if (sortOrder == Constants.SORT_BY_NAME) {
            recipesList.sortedBy { it.name }
        } else {
            recipesList.sortedByDescending { it.lastUpdated }
        }
    }

    private fun getRecipesByQuery(recipesList: List<Recipe>, query: String) =
        recipesList.filter { element ->
            checkStringsLikeness(
                element.name,
                query
            ) || checkStringsLikeness(
                element.description,
                query
            ) || checkStringsLikeness(element.instructions, query)
        }

    private fun checkStringsLikeness(string: String?, query: String) = if (!string.isNullOrEmpty())
        string.contains(query, ignoreCase = true) else false*/

}
