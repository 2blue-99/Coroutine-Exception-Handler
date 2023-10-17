package com.example.data.model

import com.example.domain.model.MyData

data class ServerResponse(
    val completed: Boolean,
    val id: String,
    val title: String,
    val userId: String
)

