package com.piny.kode_recipes_test.di

import com.piny.kode_recipes_test.ui.viewmodels.RecipeDetailsViewModel
import com.piny.kode_recipes_test.ui.viewmodels.RecipeImageViewModel
import com.piny.kode_recipes_test.ui.viewmodels.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { RecipesListViewModel(get()) }
    viewModel { RecipeDetailsViewModel(get()) }
    viewModel { RecipeImageViewModel(get()) }
}