package com.elenium.ingreditest.searchresult.presenter

import com.elenium.ingreditest.network.RecipeRepository
import com.elenium.ingreditest.core.BasePresenter
import com.elenium.ingreditest.network.RepositoryCallback
import com.elenium.ingreditest.searchresult.model.Recipe
import com.elenium.ingreditest.searchresult.view.View

class SearchResultsPresenter(private val repository: RecipeRepository) :
        BasePresenter<View>() {
    private var recipes: List<Recipe>? = null

    // 1
    fun search(query: String) {
        view?.showLoading()
        // 2
        repository.getRecipes(query, object : RepositoryCallback<List<Recipe>> {
            // 3
            override fun onSuccess(recipes: List<Recipe>?) {
                this@SearchResultsPresenter.recipes = recipes
                if (recipes != null && recipes.isNotEmpty()) {
                    view?.showRecipes(recipes)
                } else {
                    view?.showEmptyRecipes()
                }
            }

            // 4
            override fun onError() {
                view?.showError()
            }
        })
    }

    // 1
    fun addFavorite(recipe: Recipe) {
        // 2
        recipe.isFavorited = true
        // 3
        repository.addFavorite(recipe)
        // 4
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }

    // 5
    fun removeFavorite(recipe: Recipe) {
        repository.removeFavorite(recipe)
        recipe.isFavorited = false
        val recipeIndex = recipes?.indexOf(recipe)
        if (recipeIndex != null) {
            view?.refreshFavoriteStatus(recipeIndex)
        }
    }
}