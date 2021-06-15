package com.piny.kode_recipes_test.data.network

import com.piny.kode_recipes_test.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideRecipesApi(retrofit: Retrofit): RecipesApi = retrofit.create(RecipesApi::class.java)