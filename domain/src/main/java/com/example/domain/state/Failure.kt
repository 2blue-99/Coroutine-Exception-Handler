package com.example.domain.state

import com.example.domain.util.Constants


sealed class Failure(val message: String){
    data object UnAuthorizeUser: Failure(Constants.TOAST_ERROR_UN_AUTHORIZE)
    data object BadRequest: Failure(Constants.TOAST_ERROR_NOT_FOUND)
    data object NetworkConnection: Failure(Constants.TOAST_ERROR_INTERNET_CONNECTED)
    class UnHandleError(message: String = Constants.TOAST_ERROR_UNHANDLED): Failure(message)
}
