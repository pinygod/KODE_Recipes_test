package com.piny.kode_recipes_test.data.network

import com.piny.kode_recipes_test.data.RecipeDetailsResponse
import com.piny.kode_recipes_test.data.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface RecipesApi {

    @GET("recipes")
    suspend fun getAllRecipes(): RecipesResponse

    @GET("recipes/{uuid}")
    suspend fun getRecipe(@Path("uuid") uuid: UUID): RecipeDetailsResponse
}