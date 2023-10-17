package com.example.data.dataSourceImpl

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.data.dataSource.DataSource
import com.example.data.extension.errorHandler
import com.example.data.model.ServerTestData
import com.example.domain.state.ResourceState
import javax.inject.Inject


/**
 * 2023-10-10
 * pureum
 */
class DataSourceImpl @Inject constructor(
    private val retrofit: DataSource
) {

    suspend fun getApiDataSource(placeName: String): ResourceState<ServerTestData> {
        val response = retrofit.getApiDataSource(placeName)
        Log.e("TAG", "getApiDataSource: ${response.body()}")
        return when(val response = response.errorHandler()){
            is ResponseErrorState.Success ->
                ResourceState.Success(data = response.data)
            is ResponseErrorState.Error ->
                ResourceState.Error(failure = response.failure)
        }
    }
}
