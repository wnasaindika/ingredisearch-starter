package com.elenium.ingreditest.searchresult.view

import com.elenium.ingreditest.searchresult.model.Recipe

interface View {
    fun showLoading()
    fun showRecipes(recipes: List<Recipe>)
    fun showEmptyRecipes()
    fun showError()
    fun refreshFavoriteStatus(recipeIndex: Int)
}