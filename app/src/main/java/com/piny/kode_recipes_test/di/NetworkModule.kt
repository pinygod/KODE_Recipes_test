package com.piny.kode_recipes_test.di

import com.piny.kode_recipes_test.data.network.provideRecipesApi
import com.piny.kode_recipes_test.data.network.provideRetrofit
import org.koin.dsl.module

val networkModule = module {
    factory { provideRecipesApi(get()) }
    single { provideRetrofit() }
}