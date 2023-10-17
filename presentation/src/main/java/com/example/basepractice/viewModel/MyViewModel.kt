package com.example.basepractice.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basepractice.base.BaseViewModel
import com.example.domain.model.MyTestData
import com.example.domain.state.Failure
import com.example.domain.state.ResourceState
import com.example.domain.useCase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

import javax.inject.Inject

/**
 * 2023-10-10
 * pureum
 */

@HiltViewModel
class MyViewModel @Inject constructor(
    private val useCase: UseCase
) : BaseViewModel() {

    @Inject
    lateinit var useCase2: UseCase



    val loading: MutableLiveData<Boolean> get() = isLoading

//    private var _liveData = MutableLiveData<MyData>()
//    val liveData: LiveData<MyData> get() = _liveData

    /// Flow
//    private val _response = MutableStateFlow<ResourceState<MyData>>(ResourceState.Loading())
//    val response: StateFlow<ResourceState<MyData>> get() = _response

    private val  _myChannel = Channel<ResourceState<MyTestData>>()
    val myChannel = _myChannel.receiveAsFlow()

    fun getApiData(placeName: String) {
        useCase(placeName).onEach { data ->
            when(data){
                is ResourceState.Success -> {
                    _myChannel.send(data)
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {
                    _myChannel.send(data)
                    isLoading.postValue(false)
                }
                else -> {
                    isLoading.postValue(true)
                }
            }
        }.catch { exception ->
            _myChannel.send(ResourceState.Error(failure = Failure.UnHandleError(exception.message ?: "")))
        }.launchIn(modelScope)
    }
}