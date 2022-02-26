package com.example.meliapp.core.search.infrastructure

import com.example.meliapp.core.search.domain.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemsAPI {
    @GET("sites/MLA/search?")
    suspend fun searchByQuery(@Query("q") query: String): Response<ItemsResponse>

}
