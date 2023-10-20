package com.example.data.extension

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.domain.util.Constants
import com.example.domain.state.Failure
import retrofit2.Response

/**
 * 2023-10-17
 * pureum
 */




inline fun <reified T: Any> Response<T>.errorHandler(): ResponseErrorState<T>{
    return try{
        when(this.code()){
            999 -> {
                when(this.message()){
                    Constants.SOCKET_EXCEPTION -> ResponseErrorState.Error(failure = Failure.SocketException)
                    Constants.HTTP_EXCEPTION -> ResponseErrorState.Error(failure = Failure.HttpException)
                    Constants.UNKNOWN_HOST_EXCEPTION -> ResponseErrorState.Error(failure = Failure.UnknownHostException)
                    Constants.SOCKET_TIMEOUT_EXCEPTION -> ResponseErrorState.Error(failure = Failure.SocketTimeOutException)
                    Constants.ILLEGAL_STATE_EXCEPTION -> ResponseErrorState.Error(failure = Failure.IllegalStateException)
                    Constants.NON_KEYWORD_EXCEPTION -> ResponseErrorState.Error(failure = Failure.NonDataException)
                    Constants.JSON_EXCEPTION -> ResponseErrorState.Error(failure = Failure.JsonException)
                    else -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                }
            }
            else-> ResponseErrorState.Success(data = this.body() as T)
        }
    }catch (all: Throwable){
        ResponseErrorState.Error(failure = Failure.UnHandleError())
    }
}