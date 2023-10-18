package com.example.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * 2023-10-18
 * pureum
 */
@Xml(name = "Map")
data class ServerTestData(
    @Element
    val dataList: DataList?,
)

@Xml(name = "SeoulRtd.citydata_ppltn")
data class DataList(
    @PropertyElement
    val AREA_NM: String,
    @PropertyElement
    val AREA_CD: String,
    @PropertyElement
    val AREA_CONGEST_LVL: String,
    @PropertyElement
    val AREA_CONGEST_MSG: String,
    @PropertyElement
    val AREA_PPLTN_MIN: String,
    @PropertyElement
    val AREA_PPLTN_MAX: String,
    @PropertyElement
    val MALE_PPLTN_RATE: String,
    @PropertyElement
    val FEMALE_PPLTN_RATE: String,
    @PropertyElement
    val PPLTN_RATE_0: String,
    @PropertyElement
    val PPLTN_RATE_10: String,
    @PropertyElement
    val PPLTN_RATE_20: String,
    @PropertyElement
    val PPLTN_RATE_30: String,
    @PropertyElement
    val PPLTN_RATE_40: String,
    @PropertyElement
    val PPLTN_RATE_50: String,
    @PropertyElement
    val PPLTN_RATE_60: String,
    @PropertyElement
    val PPLTN_RATE_70: String,
    @PropertyElement
    val RESNT_PPLTN_RATE: String,
    @PropertyElement
    val NON_RESNT_PPLTN_RATE: String,
    @PropertyElement
    val REPLACE_YN: String,
    @PropertyElement
    val PPLTN_TIME: String,
    @PropertyElement
    val FCST_YN: String,
)