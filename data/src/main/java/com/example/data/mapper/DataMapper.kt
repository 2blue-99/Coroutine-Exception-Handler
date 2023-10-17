package com.example.data.mapper

import com.example.data.model.ServerTestData
import com.example.domain.model.MyTestData

/**
 * 2023-10-17
 * pureum
 */
object DataMapper {
    fun mapperToMyData(serverTestData: ServerTestData): MyTestData =
        MyTestData(
            areaName = serverTestData.dataList.AREA_NM,
            areaStateLevel = serverTestData.dataList.AREA_CONGEST_LVL,
            areaStateMassage = serverTestData.dataList.AREA_CONGEST_MSG,
            areaMin = serverTestData.dataList.AREA_PPLTN_MIN,
            areaMax = serverTestData.dataList.AREA_PPLTN_MAX,
            maleRate = serverTestData.dataList.MALE_PPLTN_RATE,
            femaleRate = serverTestData.dataList.FEMALE_PPLTN_RATE,
            rate0 = serverTestData.dataList.PPLTN_RATE_0,
            rate10 = serverTestData.dataList.PPLTN_RATE_10,
            rate20 = serverTestData.dataList.PPLTN_RATE_20,
            rate30 = serverTestData.dataList.PPLTN_RATE_30,
            rate40 = serverTestData.dataList.PPLTN_RATE_40,
            rate50 = serverTestData.dataList.PPLTN_RATE_50,
            rate60 = serverTestData.dataList.PPLTN_RATE_60,
            rate70 = serverTestData.dataList.PPLTN_RATE_70,
            resntRate = serverTestData.dataList.RESNT_PPLTN_RATE,
            nonResntRate = serverTestData.dataList.NON_RESNT_PPLTN_RATE,
            updateTime = serverTestData.dataList.PPLTN_TIME

        )
}