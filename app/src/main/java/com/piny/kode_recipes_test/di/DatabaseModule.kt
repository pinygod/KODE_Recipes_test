package com.piny.kode_recipes_test.di

import com.piny.kode_recipes_test.data.database.RecipeDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), RecipeDatabase::class.java, "recipes-db")
            .build()
    }
    single { get<RecipeDatabase>().getRecipeDao() }
}