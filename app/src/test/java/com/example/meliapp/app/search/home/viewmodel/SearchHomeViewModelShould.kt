package com.example.meliapp.app.search.home.viewmodel

import com.example.meliapp.utils.BaseUnitTest
import com.example.meliapp.utils.getValueForTest
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

private const val VALID_QUERY_STRING = "valid string"
private const val INVALID_QUERY_STRING = ""

class SearchHomeViewModelShould : BaseUnitTest() {
    @Test
    fun `send true when have a valid searchQuery`() {
        val viewModel = SearchHomeViewModel()

        viewModel.tryToMakeSearch(VALID_QUERY_STRING)
        val value = viewModel.isValidSearchQuery.getValueForTest()

        assertTrue(value?.getContentIfNotHandled() ?: false)
    }

    @Test
    fun `send false when have a invalid searchQuery`() {
        val viewModel = SearchHomeViewModel()

        viewModel.tryToMakeSearch(INVALID_QUERY_STRING)
        val value = viewModel.isValidSearchQuery.getValueForTest()

        assertFalse(value?.getContentIfNotHandled() ?: true)
    }
}