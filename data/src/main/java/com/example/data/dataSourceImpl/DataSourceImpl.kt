package com.example.data.dataSourceImpl

import com.devsurfer.data.state.ResponseErrorState
import com.example.data.dataSource.DataSource
import com.example.data.extension.errorHandler
import com.example.data.model.ServerResponse
import com.example.domain.state.ResourceState
import retrofit2.Retrofit
import retrofit2.create
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


/**
 * 2023-10-10
 * pureum
 */
class DataSourceImpl @Inject constructor(
    private val retrofit: DataSource
) {

    suspend fun getApiDataSource(id: String): ResourceState<ServerResponse> {
        return when(val response = retrofit.getApiDataSource(id).errorHandler()){
            is ResponseErrorState.Success ->
                ResourceState.Success(data = response.data)
            is ResponseErrorState.Error ->
                ResourceState.Error(failure = response.failure)
        }
    }
}
