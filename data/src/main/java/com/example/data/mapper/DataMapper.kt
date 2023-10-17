package com.example.data.mapper

import com.example.data.model.ServerResponse
import com.example.domain.model.MyData

/**
 * 2023-10-17
 * pureum
 */
object DataMapper {
    fun mapperToMyData(serverResponse: ServerResponse): MyData =
        MyData(
            completed = serverResponse.completed,
            id = serverResponse.id,
            title = serverResponse.title,
            userId = serverResponse.userId
        )
}