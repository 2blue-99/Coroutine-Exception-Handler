package com.example.data.extension

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.data.model.ServerTestData
import com.example.domain.state.Failure
import retrofit2.Response

/**
 * 2023-10-19
 * pureum
 */

inline fun <reified T : Any> Response<T>.personalHandler(): Failure {
    return when (this.message()) {
        "OK" -> Failure.NoException
        "INFO-200" -> Failure.NonKeywordRequest
        "ERROR-500" -> Failure.ServerErr
        "INFO-100" -> Failure.ExpiredKeyErr
        "ERROR-300" -> Failure.UnHandleError()
        "ERROR-301" -> Failure.UnHandleError()
        "ERROR-310" -> Failure.UnHandleError()
        "ERROR-331" -> Failure.UnHandleError()
        "ERROR-332" -> Failure.UnHandleError()
        "ERROR-333" -> Failure.UnHandleError()
        "ERROR-334" -> Failure.UnHandleError()
        "ERROR-335" -> Failure.UnHandleError()
        "ERROR-336" -> Failure.UnHandleError()
        "ERROR-600" ->Failure.UnHandleError()
        else -> Failure.UnHandleError()
    }
}