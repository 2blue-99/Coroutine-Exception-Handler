package com.example.domain.state

import com.example.domain.util.Constants


sealed class Failure(val message: String){
    data object UnAuthorizeUser: Failure(Constants.TOAST_ERROR_UN_AUTHORIZE)
    data object BadRequest: Failure(Constants.TOAST_ERROR_NOT_FOUND)
    data object NetworkConnection: Failure(Constants.TOAST_ERROR_INTERNET_CONNECTED)
    data object NonKeywordRequest: Failure(Constants.TOAST_ERROR_NON_KEYWORD)
    data object ServerErr: Failure(Constants.TOAST_ERROR_NON_KEYWORD)
    data object ExpiredKeyErr: Failure(Constants.TOAST_ERROR_EXPIRED_KEY_ERR)
    class UnHandleError(message: String = Constants.TOAST_ERROR_UNHANDLED): Failure(message)
}
