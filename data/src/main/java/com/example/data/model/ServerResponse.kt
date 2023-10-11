package com.example.data.model

import com.example.domain.model.MyData

data class ServerResponse(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)

fun ServerResponse.toMyData(): MyData = MyData(completed, id.toString(), title, userId.toString())