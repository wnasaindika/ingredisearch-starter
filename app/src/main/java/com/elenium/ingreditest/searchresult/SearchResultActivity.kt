package com.elenium.ingreditest.searchresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.elenium.ingreditest.*
import com.elenium.ingreditest.core.ChildActivity
import com.elenium.ingreditest.core.adapter.RecipeAdapter
import com.elenium.ingreditest.network.RecipeRepositoryImpl
import com.elenium.ingreditest.recipes.recipeIntent
import com.elenium.ingreditest.searchresult.model.Recipe
import com.elenium.ingreditest.searchresult.presenter.SearchResultsPresenter
import com.elenium.ingreditest.searchresult.view.View
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*
import kotlinx.android.synthetic.main.view_noresults.*

fun Context.searchResultsIntent(query: String): Intent {
    return Intent(this, SearchResultsActivity::class.java).apply {
        putExtra(EXTRA_QUERY, query)
    }
}
private const val EXTRA_QUERY = "EXTRA_QUERY"

class SearchResultsActivity : ChildActivity(), View {

    private val presenter: SearchResultsPresenter by lazy {SearchResultsPresenter(RecipeRepositoryImpl.getRepository(this))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val query = intent.getStringExtra(EXTRA_QUERY)
        supportActionBar?.subtitle = query

        // 2
        presenter.attachView(this)
        // 3
        presenter.search(query)
        retry.setOnClickListener { presenter.search(query) }
    }
    override fun showEmptyRecipes() {
        loadingContainer.visibility = android.view.View.GONE
        errorContainer.visibility = android.view.View.GONE
        list.visibility = android.view.View.VISIBLE
        noresultsContainer.visibility = android.view.View.VISIBLE
    }

    override fun showRecipes(recipes: List<Recipe>) {
        loadingContainer.visibility = android.view.View.GONE
        errorContainer.visibility = android.view.View.GONE
        list.visibility = android.view.View.VISIBLE
        noresultsContainer.visibility = android.view.View.GONE

        setupRecipeList(recipes)
    }

    override fun showLoading() {
        loadingContainer.visibility = android.view.View.VISIBLE
        errorContainer.visibility = android.view.View.GONE
        list.visibility = android.view.View.GONE
        noresultsContainer.visibility = android.view.View.GONE
    }

    override fun showError() {
        loadingContainer.visibility = android.view.View.GONE
        errorContainer.visibility = android.view.View.VISIBLE
        list.visibility = android.view.View.GONE
        noresultsContainer.visibility = android.view.View.GONE
    }

    override fun refreshFavoriteStatus(recipeIndex: Int) {
        list.adapter.notifyItemChanged(recipeIndex)
    }

    private fun setupRecipeList(recipes: List<Recipe>) {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = RecipeAdapter(recipes, object : RecipeAdapter.Listener {
            override fun onClickItem(recipe: Recipe) {
                startActivity(recipeIntent(recipe.sourceUrl))
            }

            override fun onAddFavorite(recipe: Recipe) {
                // 1
                presenter.addFavorite(recipe)
            }

            override fun onRemoveFavorite(recipe: Recipe) {
                // 2
                presenter.removeFavorite(recipe)
            }
        })
    }
}

