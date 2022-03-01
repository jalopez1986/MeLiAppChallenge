package com.example.meliapp.core.search.actions

import com.example.meliapp.core.search.domain.ItemsRepository
import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchItemsByQueryShould : BaseUnitTest() {
    private val ANY_QUERY_STRING = "Any"
    private val repository: ItemsRepository = mock()

    private val expected: ItemsResponse = mock()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun `when execute is called then the repository searchByQuery method is invoke`() =
        runBlockingTest {
            val searchItemsByQuery = SearchItemsByQuery(repository)

            searchItemsByQuery.execute(ANY_QUERY_STRING)

            verify(repository, times(1)).searchByQuery(ANY_QUERY_STRING)
        }

    @Test
    fun `when execute is called then the repository emits the itemResponse`() = runBlockingTest {
        val searchItemsByQuery = mockSuccessfulCase()

        assertEquals(
            Result.success(expected),
            searchItemsByQuery.execute(ANY_QUERY_STRING).first().getOrNull()
        )


        //assertEquals(itemsResponse, searchItemsByQuery.execute(ANY_QUERY_STRING).first().getOrNull() )
        //Nota: El assert deberia ser contra esta línea pero al parecer hay un bug si se usa
        //la clase Result de Kotlin
        //https://youtrack.jetbrains.com/issue/KT-30223
        //https://stackoverflow.com/questions/65420765/problems-with-kotlin-resultt-on-unit-tests
    }

    @Test
    fun `when execute is called then the repository propagates the exception`() = runBlockingTest {
        val searchItemsByQuery = mockErrorCase()

        assertEquals(
            Result.failure<ItemsResponse>(exception),
            searchItemsByQuery.execute(ANY_QUERY_STRING).first().getOrNull()
        )

    }

    private suspend fun mockSuccessfulCase(): SearchItemsByQuery {
        whenever(repository.searchByQuery(ANY_QUERY_STRING)).thenReturn(
            Result.success(expected)
        )
        return SearchItemsByQuery(repository)
    }

    private suspend fun mockErrorCase(): SearchItemsByQuery {
        whenever(repository.searchByQuery(ANY_QUERY_STRING)).thenReturn(
            Result.failure(exception)
        )

        return SearchItemsByQuery(repository)
    }

}