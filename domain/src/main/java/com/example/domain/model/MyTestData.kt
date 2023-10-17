package com.example.domain.model

/**
 * 2023-10-18
 * pureum
 */
data class MyTestData(
    val areaName: String,
    // 현재 인구 상태 (여유, 혼잡 등등)
    val areaStateLevel: String,
    // 인구 상태 코멘트
    val areaStateMassage: String,
    // 인구 최소값
    val areaMin: String,
    // 인구 맥스값
    val areaMax: String,
    // 남성 비율
    val maleRate: String,
    //여성 비율
    val femaleRate: String,
    val rate0: String,
    val rate10: String,
    val rate20: String,
    val rate30: String,
    val rate40: String,
    val rate50: String,
    val rate60: String,
    val rate70: String,
    // 상주 인원 비율
    val resntRate: String,
    // 비 상주인원 비율
    val nonResntRate: String,
    // 해당 데이터 업데이트 시간
    val updateTime: String,
)