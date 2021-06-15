package com.piny.kode_recipes_test.di

import com.piny.kode_recipes_test.data.repository.ImageRepository
import com.piny.kode_recipes_test.data.repository.RecipesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        RecipesRepository(get(), get(), get())
    }
    single {
        ImageRepository(get())
    }
}