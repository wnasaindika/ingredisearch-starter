package com.elenium.ingreditest

import android.content.SharedPreferences
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import com.elenium.ingreditest.network.RecipeRepository
import com.elenium.ingreditest.network.RecipeRepositoryImpl
import com.elenium.ingreditest.searchresult.model.Recipe
import org.junit.Before
import org.junit.Test

class RepositoryTests {
    private lateinit var spyRepository: RecipeRepository
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        // 1
        sharedPreferences = mock()
        sharedPreferencesEditor = mock()
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

        // 2
        spyRepository = spy(RecipeRepositoryImpl(sharedPreferences))
    }

    @Test
    fun addFavorite_withEmptyRecipes_savesJsonRecipe() {
        // 1
        doReturn(emptyList<Recipe>()).whenever(spyRepository).getFavoriteRecipes()

        // 2
        val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
        spyRepository.addFavorite(recipe)

        // 3
        inOrder(sharedPreferencesEditor) {
            // 4
            val jsonString = Gson().toJson(listOf(recipe))
            verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
            verify(sharedPreferencesEditor).apply()
        }
    }
}