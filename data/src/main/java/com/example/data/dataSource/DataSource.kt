package com.example.data.dataSource

import com.example.data.model.ServerResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 2023-10-10
 * pureum
 */
interface DataSource {
    @GET("todos/{id}")
    suspend fun getApiDataSource(
        @Path("id") id: String
    ):Response<ServerResponse>
}