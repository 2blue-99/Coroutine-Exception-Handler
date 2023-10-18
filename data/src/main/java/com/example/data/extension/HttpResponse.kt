package com.example.data.extension

import android.util.Log
import com.devsurfer.data.state.ResponseErrorState
import com.example.domain.util.Constants
import com.example.domain.state.Failure
import retrofit2.Response
import java.io.IOException

/**
 * 2023-10-17
 * pureum
 */

inline fun <reified T: Any> Response<T>.errorHandler(): ResponseErrorState<T> {
    return try{
        when(this.code()){
            // 에러코드 분기 처리 (401, 404)
            Constants.CODE_UN_AUTHORIZE -> ResponseErrorState.Error(failure = Failure.UnAuthorizeUser)
            Constants.CODE_BAD_REQUEST -> ResponseErrorState.Error(failure = Failure.BadRequest)
            else->{
                // 메세지 분기 처리
                return when(this.message()){
                    "SUCCESS" -> {
                        val body = this.body()
                        return if(body == null) ResponseErrorState.Error(failure = Failure.UnHandleError())
                        else ResponseErrorState.Success(data = body as T)
                    }
                    "INFO-200" -> ResponseErrorState.Error(failure = Failure.NonKeywordRequest)
                    "ERROR-500" -> ResponseErrorState.Error(failure = Failure.ServerErr)
                    "INFO-100" -> ResponseErrorState.Error(failure = Failure.ExpiredKeyErr)
                    "ERROR-300" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-301" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-310" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-331" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-332" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-333" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-334" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-335" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-336" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    "ERROR-600" -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                    else -> ResponseErrorState.Error(failure = Failure.UnHandleError())
                }
            }
        }
    }catch (e: IOException){
        // IOException 인터넷 연결 에러 처리
        Log.e("TAG", "errorHandler: ${this}", )
        ResponseErrorState.Error(failure = Failure.NetworkConnection)
    }catch (t: Throwable){
        // 알 수 없는 에러 처리
        Log.e("TAG", "errorHandler: ${this}", )
        ResponseErrorState.Error(failure = Failure.UnHandleError())
    }
}