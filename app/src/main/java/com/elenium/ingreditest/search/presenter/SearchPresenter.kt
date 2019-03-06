package com.elenium.ingreditest.search.presenter

import com.elenium.ingreditest.search.view.View

class SearchPresenter {
    private var view: View? = null

    fun onAttached(view: View) {
        this.view = view;
    }

    fun onDeatahced() {
        this.view = null;
    }

    fun search(query: String) {
        if(query.trim().isBlank())
        {
            this.view?.showQueryRequiredMessage()
        }else{
            this.view?.showSearchResult(query)
        }
    }
}

