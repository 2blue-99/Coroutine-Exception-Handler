package com.example.data.util

import android.util.Log
import android.util.Xml
import com.example.domain.exception.NonDataException
import com.example.domain.exception.ServerErrException
import com.example.domain.util.Constants
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import javax.inject.Inject


/**
 * 2023-08-29
 * pureum
 */
class NetworkInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        val responseBody = response.body
        val responseBodyString = responseBody?.string()

        checkXml(responseBodyString?.byteInputStream())
        return response.newBuilder()
            .body((responseBodyString ?: "").toResponseBody(responseBody?.contentType()))
            .message(checkXml(responseBodyString?.byteInputStream()))
            .build()
    }
    private fun checkXml(inputStream: InputStream?): String {
        try {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    when(parser.name){
                        "RESULT.CODE" -> return getNext(parser)
                        "SeoulRtd.citydata_ppltn" -> return "SUCCESS"
                    }
                }
                parser.next()
                eventType = parser.eventType  // 반드시 호출 전에 확인
            }
        } catch (E: Exception) {
            return "ERR"
        }
        return "ERR"
    }
    private fun getNext(parser: XmlPullParser): String {
        return when (parser.next()) {
            XmlPullParser.TEXT -> parser.text
            else -> ""
        }
    }
}
