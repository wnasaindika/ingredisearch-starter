package com.elenium.ingreditest

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.elenium.ingreditest.network.RecipeRepository
import com.elenium.ingreditest.searchresult.presenter.SearchResultsPresenter
import com.elenium.ingreditest.searchresult.view.View
import org.junit.Before
import org.junit.Test

class SearchResultTest {

    private lateinit var repository: RecipeRepository
    private lateinit var view: View
    private lateinit var searchResultsPresenter: SearchResultsPresenter

    @Before
    fun setUp() {
        repository = mock()
        view = mock()
        searchResultsPresenter = SearchResultsPresenter(repository)
        searchResultsPresenter.attachView(view)
    }
    // 1
    @Test
    fun search_callsShowLoading() {
        searchResultsPresenter.search("eggs")
        verify(view).showLoading()
    }

    // 2
    @Test
    fun search_callsGetRecipes() {
        searchResultsPresenter.search("eggs")
        verify(repository).getRecipes(eq("eggs"), any())
    }
}