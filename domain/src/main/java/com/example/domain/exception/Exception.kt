package com.example.domain.exception

/**
 * 2023-10-18
 * pureum
 */

class NonDataException(code: String): Exception(code)
class ServerErrException(code: String): Exception(code)
class KeyExpiredException(code: String): Exception(code)