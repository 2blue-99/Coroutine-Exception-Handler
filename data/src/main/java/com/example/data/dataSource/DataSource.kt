package com.example.data.dataSource

import com.example.data.model.ServerTestData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 2023-10-10
 * pureum
 */
interface DataSource {
    @GET("citydata_ppltn/1/5/{placeName}")
    suspend fun getApiDataSource(
        @Path("placeName") placeName: String
    ):Response<ServerTestData>
}