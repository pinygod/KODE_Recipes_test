package com.piny.kode_recipes_test.di

import com.piny.kode_recipes_test.utils.Connectivity
import org.koin.dsl.module

val utilsModule = module {
    single { Connectivity(get()) }
}