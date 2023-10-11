package com.example.basepractice.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basepractice.base.BaseViewModel
import com.example.domain.model.MyData
import com.example.domain.useCase.FlowUseCase
import com.example.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import retrofit2.HttpException
import java.net.SocketException
import javax.inject.Inject

/**
 * 2023-10-10
 * pureum
 */

@HiltViewModel
class MyViewModel @Inject constructor(
    private val useCase: UseCase,
    private val flowUseCase: FlowUseCase
//    private val useCase: UseCase,
) : BaseViewModel() {

    val loading: MutableLiveData<Boolean> get() = isLoading

    private var _liveData = MutableLiveData<MyData>()
    val liveData: LiveData<MyData> get() = _liveData
    private var count = 1

    fun getApiData() {
        modelScope.launch {
            repeat(50) {
                isLoading.postValue(true)
                withTimeoutOrNull(waitTime) {
                    _liveData.value = useCase(count++)
                }
                isLoading.postValue(false)
                delay(1000L)
            }
        }
    }

    fun getSocketException() {
        modelScope.launch {
            _liveData.value = useCase(-1)
        }
    }

    fun getTimeOutException() {
        modelScope.launch {
            _liveData.value = useCase(-2)
        }
    }






    /// Flow
    private var _response = MutableStateFlow(MyData(true, "", "", ""))
    val response: StateFlow<MyData> get() = _response

    fun getFlowApiData() {
        modelScope.launch {
            flowUseCase.invoke()
                .collectLatest {
                    if(it.id=="-1") isLoading.postValue(true)
                    else {
                        isLoading.postValue(false)
                        _response.value = it
                    }
                }
        }
    }
}