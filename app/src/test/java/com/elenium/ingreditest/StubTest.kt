package com.elenium.ingreditest

import com.nhaarman.mockito_kotlin.*
import com.elenium.ingreditest.network.RecipeRepository
import com.elenium.ingreditest.network.RepositoryCallback
import com.elenium.ingreditest.searchresult.model.Recipe
import com.elenium.ingreditest.searchresult.presenter.SearchResultsPresenter
import com.elenium.ingreditest.searchresult.view.View
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StubTest {

    private lateinit var repository: RecipeRepository
    private lateinit var presenter: SearchResultsPresenter
    private lateinit var view: View

    @Before
    fun setUp() {
        repository = mock()
        view = mock()
        presenter = SearchResultsPresenter(repository)
        presenter.attachView(view)
    }

    @Test
    fun search_withRepositoryHavingRecipes_callsShowRecipes() {
        val recipe = Recipe("recipeId", "title", "imageUrl", "sourceUrl", false)
        val recipes = listOf<Recipe>(recipe)

        doAnswer {
            val callback: RepositoryCallback<List<Recipe>> = it.getArgument(1)
            callback.onSuccess(recipes)
        }.whenever(repository).getRecipes(eq("eggs"), any())

        presenter.search("eggs")

        verify(view).showRecipes(recipes)
    }

    @Test
    fun addFavorite_shouldUpdateRecipeStatus() {
        // 1
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)

        // 2
        presenter.addFavorite(recipe)

        // 3
        Assert.assertTrue(recipe.isFavorited)
    }
}