package com.elenium.ingreditest.search

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.inputmethod.InputMethodManager
import com.elenium.ingreditest.R
import com.elenium.ingreditest.core.ChildActivity
import com.elenium.ingreditest.search.presenter.SearchPresenter
import com.elenium.ingreditest.search.view.View
import com.elenium.ingreditest.searchresult.searchResultsIntent
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : ChildActivity(),View {

    private val presenter:SearchPresenter = SearchPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        presenter.onAttached(this)

        searchButton.setOnClickListener {
            val query = ingredients.text.toString()
            // 3
            presenter.search(query)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDeatahced()
    }

    override fun showQueryRequiredMessage() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        Snackbar.make(searchButton, getString(R.string.search_query_required), Snackbar
                .LENGTH_LONG).show()
    }

    override fun showSearchResult(query: String) {
        startActivity(searchResultsIntent(query))
    }
}