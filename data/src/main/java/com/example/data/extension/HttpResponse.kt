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
            // 401, 404 에러 처리
            Constants.CODE_UN_AUTHORIZE -> ResponseErrorState.Error(failure = Failure.UnAuthorizeUser)
            Constants.CODE_BAD_REQUEST -> ResponseErrorState.Error(failure = Failure.BadRequest)
            else->{
                val body = this.body()
                return if(body == null){
                    // body null Err -> 알 수 없는 에러 처리
                    ResponseErrorState.Error(failure = Failure.UnHandleError())
                }else{
                    // 정상 반환 값 에러 처리
                    ResponseErrorState.Success(data = body as T)
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