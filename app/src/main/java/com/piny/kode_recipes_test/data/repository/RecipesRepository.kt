package com.piny.kode_recipes_test.data.repository

import com.piny.kode_recipes_test.data.database.RecipeDao
import com.piny.kode_recipes_test.data.network.RecipesApi
import com.piny.kode_recipes_test.data.utils.networkBound
import com.piny.kode_recipes_test.utils.Connectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*

class RecipesRepository(
    private val api: RecipesApi,
    private val recipeDao: RecipeDao,
    private val connectivity: Connectivity
) {
    suspend fun getRecipe(uuid: UUID) = withContext(Dispatchers.IO) {
        networkBound(
            query = { recipeDao.getRecipe(uuid) },
            fetch = { api.getRecipe(uuid) },
            saveFetchResult = { recipeDao.deleteAndInsertRecipe(it.recipe) },
            shouldFetch = { connectivity.isOnline() }
        )
    }

    suspend fun getAllRecipes(searchQuery: String, sortOrder: String) =
        networkBound(
            query = { recipeDao.getRecipes(searchQuery, sortOrder) },
            fetch = { api.getAllRecipes() },
            saveFetchResult = { recipeDao.deleteAndInsertAll(it.recipes) },
            shouldFetch = { connectivity.isOnline() } // maybe add some kind of timer to delay updates from server
        ).flowOn(Dispatchers.IO)


    /*suspend fun getRecipe(uuid: UUID) = withContext(Dispatchers.IO) { api.getRecipe(uuid).recipe } // use without database

    suspend fun getAllRecipes() = withContext(Dispatchers.IO) { api.getAllRecipes().recipes }*/

}