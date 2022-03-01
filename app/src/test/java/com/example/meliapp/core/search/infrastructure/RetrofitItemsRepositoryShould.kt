package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class RetrofitItemsRepositoryShould : BaseUnitTest() {
    private val ANY_QUERY_STRING = "Any"

    private val api: ItemsAPI = mock()

    private val itemResponse: ItemsResponse = mock()

    @Test
    fun `when searchByQuery is executed then the api execute the searchByQuery method`() =
        runBlockingTest {
            val repository = RetrofitItemsRepository(api)

            repository.searchByQuery(ANY_QUERY_STRING)

            verify(api, times(1)).searchByQuery(ANY_QUERY_STRING)

        }

    @Test
    fun `emit Result success when api return successful response`() = runBlockingTest() {
        val repository = mockSuccessfulCase()

        assertEquals(Result.success(itemResponse), repository.searchByQuery(ANY_QUERY_STRING))
    }

    @Test
    fun `propagate a ApiResponseException when api response is not successful`() = runBlockingTest {
        val repository = mockFailureCase()

        assertEquals(
            "API response error",
            repository.searchByQuery(ANY_QUERY_STRING).exceptionOrNull()?.message
        )
    }

    @Test
    fun `propagate a GenericNetworkException when api throw any exception`() = runBlockingTest {
        val repository = mockThrowExceptionApi()

        assertEquals(
            "Generic Network Error",
            repository.searchByQuery(ANY_QUERY_STRING).exceptionOrNull()?.message
        )

    }

    private suspend fun mockSuccessfulCase(): RetrofitItemsRepository {
        whenever(api.searchByQuery(ANY_QUERY_STRING)).thenReturn(
            Response.success(itemResponse)
        )

        return RetrofitItemsRepository(api)
    }

    private suspend fun mockFailureCase(): RetrofitItemsRepository {
        val response = Response.error<ItemsResponse>(
            403,
            ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}"
            )
        )

        whenever(api.searchByQuery(ANY_QUERY_STRING)).thenReturn(
            response
        )

        return RetrofitItemsRepository(api)
    }

    private suspend fun mockThrowExceptionApi(): RetrofitItemsRepository {
        whenever(api.searchByQuery(ANY_QUERY_STRING)).thenThrow(RuntimeException("network error"))

        return RetrofitItemsRepository(api)
    }


}