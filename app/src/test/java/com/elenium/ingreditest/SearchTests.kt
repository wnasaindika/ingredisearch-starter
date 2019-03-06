package com.elenium.ingreditest

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.elenium.ingreditest.search.presenter.SearchPresenter
import com.elenium.ingreditest.search.view.View
import org.junit.Before
import org.junit.Test

class SearchTests {
    private lateinit var searchPresenter: SearchPresenter
    private lateinit var view: View

    @Before
    fun setup() {
        searchPresenter = SearchPresenter()
        view = mock()
        searchPresenter.onAttached(view)
    }

    @Test
    fun search_withEmptyQuery_callsShowQueryRequiredMessage() {
        searchPresenter.search("")
        verify(view).showQueryRequiredMessage()
    }

    @Test
    fun search_withEmptyQuery_callShowSearchResults()
    {
        searchPresenter.search("")
        verify(view, never()).showSearchResult(any())
    }
}