package com.example.data.dataSourceImpl

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.data.dataSource.DataSource
import com.example.data.extension.errorHandler
import com.example.data.model.ServerData
import com.example.domain.state.Failure
import com.example.domain.state.ResourceState
import javax.inject.Inject


/**
 * 2023-10-10
 * pureum
 */
class DataSourceImpl @Inject constructor(
    private val retrofit: DataSource
) {

    suspend fun getApiDataSource(placeName: String): ResourceState<ServerData> {
        return when(val response = retrofit.getApiDataSource(placeName).errorHandler()){
            is ResponseErrorState.Success -> {
                ResourceState.Success(data = response.data)
            }
            is ResponseErrorState.Error ->
                ResourceState.Error(failure = response.failure)
        }
    }
}
