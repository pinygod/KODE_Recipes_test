package com.piny.kode_recipes_test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.piny.kode_recipes_test.ui.adapters.RecipeBriefAdapter
import com.piny.kode_recipes_test.data.RecipeDetails
import com.piny.kode_recipes_test.databinding.FragmentRecipeDetailsBinding
import com.piny.kode_recipes_test.extensions.onItemSelected
import com.piny.kode_recipes_test.extensions.showMessage
import com.piny.kode_recipes_test.utils.Result
import com.piny.kode_recipes_test.ui.viewmodels.RecipeDetailsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RecipeDetailsFragment : Fragment(), RecipeBriefAdapter.OnItemClickListener {

    private lateinit var binding: FragmentRecipeDetailsBinding
    private val viewModel by viewModel<RecipeDetailsViewModel>()
    private lateinit var recipeUuid: UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeUuid = UUID.fromString(RecipeDetailsFragmentArgs.fromBundle(it).recipeUuid)
            viewModel.setRecipe(recipeUuid)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { recipeResult ->
                    when (recipeResult) {
                        is Result.Success -> setupBinding(recipeResult.data!!)
                        is Result.Error -> onErrorWhileLoadingRecipe(recipeResult)
                    }
                }
            }
        }
    }

    private fun setupBinding(recipe: RecipeDetails) {
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            this.recipe = recipe
            similarsRecycler.adapter =
                RecipeBriefAdapter(this@RecipeDetailsFragment).apply { submitList(recipe.similar) }

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.getRecipeSlides()?.let {
                imageSlider.setImageList(
                    it,
                    ScaleTypes.CENTER_CROP
                )
            }
            imageSlider.onItemSelected {
                navigateToRecipeImage(recipe.images.elementAt(it))
            }
        }
    }

    private fun navigateToRecipeImage(imagePath: String) {
        findNavController().navigate(
            RecipeDetailsFragmentDirections.actionRecipeDetailsToRecipeImage(imagePath)
        )
    }

    private fun onErrorWhileLoadingRecipe(recipeResult: Result.Error<RecipeDetails>) {
        recipeResult.throwable?.localizedMessage?.let {
            binding.root.showMessage(it)
        }

        findNavController().popBackStack()
    }

    override fun onItemClick(recipeUuid: UUID) {
        findNavController().navigate(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentSelf(recipeUuid.toString())
        )
    }
}