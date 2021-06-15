package com.piny.kode_recipes_test.data.database

import androidx.room.*
import com.piny.kode_recipes_test.data.Recipe
import com.piny.kode_recipes_test.data.RecipeDetails
import com.piny.kode_recipes_test.utils.Constants
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Recipe>)

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()

    @Transaction
    suspend fun deleteAndInsertAll(list: List<Recipe>) {
        deleteAll()
        insertAll(list)
    }

    @Query("SELECT * FROM recipes")
    fun getRecipes() : Flow<List<Recipe>>

    fun getRecipes(query: String, order: String) =
        if (order == Constants.SORT_BY_DATE) {
            getRecipesOrderedByDate(query)
        } else {
            getRecipesOrderedByName(query)
        }

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR instructions LIKE '%' || :query || '%' ORDER BY name")
    fun getRecipesOrderedByName(query: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR instructions LIKE '%' || :query || '%' ORDER BY last_updated DESC")
    fun getRecipesOrderedByDate(query: String): Flow<List<Recipe>>

    @Query("SELECT * FROM detailed_recipes WHERE uuid=:uuid")
    suspend fun getRecipe(uuid: UUID): RecipeDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeDetails: RecipeDetails)

    @Delete
    suspend fun deleteRecipe(recipeDetails: RecipeDetails)

    @Transaction
    suspend fun deleteAndInsertRecipe(recipeDetails: RecipeDetails) {
        deleteRecipe(recipeDetails)
        insertRecipe(recipeDetails)
    }

}