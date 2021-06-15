package com.piny.kode_recipes_test.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.piny.kode_recipes_test.data.RecipeBrief
import com.piny.kode_recipes_test.databinding.RecipeBriefRecyclerviewItemBinding
import java.util.*


class RecipeBriefAdapter(private val listener: OnItemClickListener) :
    ListAdapter<RecipeBrief, RecipeBriefAdapter.RecipeBriefViewHolder>(Companion) {

    class RecipeBriefViewHolder(val binding: RecipeBriefRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<RecipeBrief>() {
        override fun areItemsTheSame(oldItem: RecipeBrief, newItem: RecipeBrief): Boolean =
            oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(oldItem: RecipeBrief, newItem: RecipeBrief): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeBriefViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipeBriefRecyclerviewItemBinding.inflate(layoutInflater, parent, false)

        return RecipeBriefViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeBriefViewHolder, position: Int) {
        val recipe = getItem(position)
        with(holder.binding) {
            this.recipe = recipe
            this.listener = this@RecipeBriefAdapter.listener
            executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(recipeUuid: UUID)
    }
}