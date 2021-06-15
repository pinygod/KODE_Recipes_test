package com.piny.kode_recipes_test.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.piny.kode_recipes_test.data.Recipe
import com.piny.kode_recipes_test.databinding.RecipesRecyclerviewItemBinding

class RecipesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder>(Companion) {

    class RecipeViewHolder(val binding: RecipesRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipesRecyclerviewItemBinding.inflate(layoutInflater, parent, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        with(holder.binding){
            this.recipe = recipe
            this.listener = this@RecipesAdapter.listener
            executePendingBindings()
        }

    }

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }
}