package com.example.basepractice.base

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basepractice.util.FetchState
import com.example.domain.util.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.plus
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException
import java.lang.NullPointerException

/**
 * 2023-10-11
 * pureum
 */
open class BaseViewModel: ViewModel() {
    protected val isLoading = MutableLiveData(false)

    private var _fetchState = MutableLiveData<Pair<Throwable, String>>()
    val fetchState : LiveData<Pair<Throwable, String>> get() = _fetchState

    private val job = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("TAG", "coroutine exception handler : $throwable: ", )
        isLoading.postValue(false)
        coroutineContext.job.cancel()
        throwable.printStackTrace()
        when(throwable){
            is SocketException -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_INTERNET_CONNECTED)
            is HttpException -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_PARSE_ERROR)
            is UnknownHostException -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_WRONG_CONNECTION)
            is SocketTimeoutException -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_SOCKET_TIMEOUT)
            is IllegalStateException -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_ILLEGAL_STATE)
            else -> _fetchState.value = Pair(throwable, Constants.TOAST_ERROR_UNHANDLED)
        }
    }
    protected val modelScope = viewModelScope + job + exceptionHandler
}