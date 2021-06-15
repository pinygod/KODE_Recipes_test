package com.piny.kode_recipes_test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piny.kode_recipes_test.data.Recipe
import com.piny.kode_recipes_test.data.RecipeDetails
import com.piny.kode_recipes_test.data.database.utils.Converters

@Database(version = 1, entities = [Recipe::class, RecipeDetails::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao
}