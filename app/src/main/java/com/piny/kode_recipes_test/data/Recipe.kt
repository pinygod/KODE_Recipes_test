package com.piny.kode_recipes_test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    val uuid: UUID,
    val name: String,
    val images: List<String>,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Int,
    val description: String?,
    var instructions: String,
    val difficulty: Int
)