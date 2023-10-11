package com.example.data.dataSourceImpl

import android.util.Log
import com.example.data.dataSource.DataSource
import com.example.data.model.ServerResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
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
    private val retrofit: Retrofit
) {

    companion object {
        private var count = 1
    }

    suspend fun getApiDataSource(id: String): ServerResponse {
        if (id == "-1") throw SocketException()
        if (id == "-2") throw TimeoutException()
        return retrofit.create(DataSource::class.java).getApiDataSource(id)
    }


    // Flow
    fun getApiFlowDataSource(): Flow<ServerResponse> {
        return flow {
            while (true) {
                emit(ServerResponse(true, -1, "", -1)) // loading을 위해
                emit(retrofit.create(DataSource::class.java).getApiFlowDataSource(count.toString()))
                count++
                delay(1000L)
            }
        }
    }


    // Response - Coroutine Exception Handler 이용
    fun getApiResponseDataSource(): Flow<ServerResponse> {
        return flow {
            while (true){

                val response = retrofit.create(DataSource::class.java).getApiResponseDataSource(count.toString())

                if(response.isSuccessful){
                    emit(response.body()!!)

                }else when(response.code()){
                    400 -> throw HttpException(response) // 400
                    401 -> throw HttpException(response) // 401
                    402 -> throw HttpException(response) // 402
                    403 -> throw HttpException(response) // 403
                    404 -> throw HttpException(response) // 404
                }
            }
        }
    }





//    // Response - Coroutine Exception Handler Sealed Class 이용
//    suspend fun getApiResponseSealedDataSource(): Flow<ServerResponse> {
//
//        // when 안에 코드를 통해 성공적인 반환인지, 핸들링 가능한 실패인지, 핸들링 불가능한 실패인지 판단하여 반환
//        return when(val response = retrofit.create(DataSource::class.java).getApiResponseSealedDataSource(count.toString()).errorHandler()){
//
//            //값이 성공인 경우 ResourceState 객체에 정상적인 반환값 태워 반환
//            is ResponseErrorState.Success ->
//                ResourceState.Success(data = AuthTokenMapper.mapperToModel(response.data))
//
//            //값이 실패인 경우 ResourceState 객체에 실패에 알맞는 Err 코드 태워 반환
//            is ResponseErrorState.Error->{
//                ResourceState.Error(failure = response.failure)
//            }
//        }
//    }
//
//
//    fun <T> Response<T>.pureum(): ResponseErrorState<T> = ResponseErrorState.Error(failure = Failure.BadRequest)
//
//    inline fun <reified T: Any> Response<T>.errorHandler(): ResponseErrorState<T>{
//        return try{
//            when(this.code()){
//                // 핸들링 가능한 예외
//                Constants.CODE_UN_AUTHORIZE -> ResponseErrorState.Error(failure = Failure.UnAuthorizeUser)
//                Constants.CODE_BAD_REQUEST -> ResponseErrorState.Error(failure = Failure.BadRequest)
//
//                else->{
//                    val body = this.body()
//                    return if(body == null){
//                        // 핸들링 불가능한 예외
//                        ResponseErrorState.Error(failure = Failure.UnHandleError())
//                    }else{
//
//                        // 정상적인 예외
//                        ResponseErrorState.Success(data = body as T)
//                    }
//                }
//            }
//        }catch (e: IOException){
//            ResponseErrorState.Error(failure = Failure.NetworkConnection)
//        }catch (t: Throwable){
//            ResponseErrorState.Error(failure = Failure.UnHandleError())
//        }
//    }
//
//    sealed class ResponseErrorState<T>{
//        data class Success<T>(val data: T): ResponseErrorState<T>()
//        data class Error<T>(val failure: Failure = Failure.UnHandleError()): ResponseErrorState<T>()
//    }

}
