package com.example.domain.state

import com.example.domain.util.Constants


sealed class Failure(val message: String){

    data object SocketException: Failure(Constants.TOAST_ERROR_WRONG_CONNECTION)
    data object HttpException: Failure(Constants.TOAST_ERROR_PARSE_ERROR)
    data object UnknownHostException: Failure(Constants.TOAST_ERROR_INTERNET_CONNECTED)
    data object SocketTimeOutException: Failure(Constants.TOAST_ERROR_SOCKET_TIMEOUT)
    data object IllegalStateException: Failure(Constants.TOAST_ERROR_ILLEGAL_STATE)
    data object NonDataException: Failure(Constants.TOAST_ERROR_NON_KEYWORD)
    data object JsonException: Failure(Constants.TOAST_ERROR_EMPTY_INPUT)
    class UnHandleError(message: String = Constants.TOAST_ERROR_UNHANDLED): Failure(message)
}
