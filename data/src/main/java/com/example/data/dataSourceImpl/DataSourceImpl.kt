package com.example.data.dataSourceImpl

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.data.dataSource.DataSource
import com.example.data.extension.errorHandler
import com.example.data.extension.personalHandler
import com.example.data.model.ServerTestData
import com.example.domain.state.Failure
import com.example.domain.state.ResourceState
import retrofit2.HttpException
import java.util.concurrent.TimeoutException
import javax.inject.Inject


/**
 * 2023-10-10
 * pureum
 */
class DataSourceImpl @Inject constructor(
    private val retrofit: DataSource
) {

    suspend fun getApiDataSource(placeName: String): ResourceState<ServerTestData> {
        val realResponse = retrofit.getApiDataSource(placeName)
        return when(val response = realResponse.errorHandler()){
            is ResponseErrorState.Success -> {
                //200인데 메시지가 다를경우
                when(val message = realResponse.personalHandler()){
                    Failure.NoException -> ResourceState.Success(data = response.data)
                    else -> ResourceState.Error(failure = message)
                }
            }
            is ResponseErrorState.Error ->
                ResourceState.Error(failure = response.failure)
        }
    }
}
