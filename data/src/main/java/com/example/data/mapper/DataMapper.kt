package com.example.data.mapper

import com.example.data.model.ServerData
import com.example.domain.model.MyTestData

/**
 * 2023-10-17
 * pureum
 */
object DataMapper {
    fun mapperToMyData(serverTestData: ServerData): MyTestData =
        MyTestData(
            areaName = serverTestData.AREA_NM,
            areaStateLevel = serverTestData.AREA_CONGEST_LVL,
            areaStateMassage = serverTestData.AREA_CONGEST_MSG,
            areaMin = serverTestData.AREA_PPLTN_MIN,
            areaMax = serverTestData.AREA_PPLTN_MAX,
            maleRate = serverTestData.MALE_PPLTN_RATE,
            femaleRate = serverTestData.FEMALE_PPLTN_RATE,
            rate0 = serverTestData.PPLTN_RATE_0,
            rate10 = serverTestData.PPLTN_RATE_10,
            rate20 = serverTestData.PPLTN_RATE_20,
            rate30 = serverTestData.PPLTN_RATE_30,
            rate40 = serverTestData.PPLTN_RATE_40,
            rate50 = serverTestData.PPLTN_RATE_50,
            rate60 = serverTestData.PPLTN_RATE_60,
            rate70 = serverTestData.PPLTN_RATE_70,
            resntRate = serverTestData.RESNT_PPLTN_RATE,
            nonResntRate = serverTestData.NON_RESNT_PPLTN_RATE,
            updateTime = serverTestData.PPLTN_TIME

        )
}