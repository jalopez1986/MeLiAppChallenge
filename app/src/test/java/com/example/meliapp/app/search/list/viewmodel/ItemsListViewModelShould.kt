package com.example.meliapp.app.search.list.viewmodel

import com.example.meliapp.core.search.actions.SearchItemsByQuery
import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.utils.BaseUnitTest
import com.example.meliapp.utils.getValueForTest
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

class ItemsListViewModelShould : BaseUnitTest() {

    private val ANY_QUERY_STRING = "Any"

    val searchItemsByQuery: SearchItemsByQuery = mock()
    val itemsResponse: ItemsResponse = mock()

    private val expected = Result.success(itemsResponse)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun `when searchItemsByQuery is executed then the searchItemsByQuery action is executed`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.searchItemsByQuery(ANY_QUERY_STRING)

        viewModel.items.getValueForTest()
        verify(searchItemsByQuery, times(1)).execute(ANY_QUERY_STRING)

    }

    @Test
    fun `when searchItemsByQuery is executed then the searchItemsByQuery action emits itemResponse`() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.searchItemsByQuery(ANY_QUERY_STRING)

        assertEquals(expected, viewModel.items.getValueForTest())
    }

    @Test
    fun `emit error when receive error`() {
        val viewModel = mockErrorCase()

        viewModel.searchItemsByQuery(ANY_QUERY_STRING)

        assertEquals(exception, viewModel.items.getValueForTest()!!.exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): ItemsListViewModel {
        runBlocking {
            whenever(searchItemsByQuery.execute(ANY_QUERY_STRING)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return ItemsListViewModel(searchItemsByQuery)
    }

    private fun mockErrorCase(): ItemsListViewModel {
        runBlocking {
            whenever(searchItemsByQuery.execute(ANY_QUERY_STRING)).thenReturn(
                flow {
                    emit(Result.failure<ItemsResponse>(exception))
                }
            )
        }
        return ItemsListViewModel(searchItemsByQuery)
    }
}


