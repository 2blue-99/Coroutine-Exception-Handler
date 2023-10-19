package com.example.data.util

import android.util.Log
import android.util.Xml
import com.example.domain.exception.NonDataException
import com.example.domain.exception.ServerErrException
import com.example.domain.util.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.xmlpull.v1.XmlPullParser
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.log


/**
 * 2023-08-29
 * pureum
 */
class NetworkInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        if (response.code == 401) {
//            getNewAccessToken(response)
        }
        val responseBody = response.body
        val responseBodyString = responseBody?.string()
//        checkXml(responseBodyString?.byteInputStream())
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
                    Log.e("TAG", "checkXml: ${parser.name}", )
                    when(parser.name){
                        "RESULT.CODE" -> return getNext(parser)
                        "CODE" -> return getNext(parser)
                        "SeoulRtd.citydata_ppltn" -> return "OK"
                    }
                }
                parser.next()
                eventType = parser.eventType  // 반드시 호출 전에 확인
            }
            return "ERR"
        } catch (E: Exception) {
            return "ERR"
        }
    }
    private fun getNext(parser: XmlPullParser): String {
        return when (parser.next()) {
            XmlPullParser.TEXT -> parser.text
            else -> ""
        }
    }

//    private fun getNewAccessToken(response: Response){
//        response.close()
//
//        var newAccessToken: String? = ""
//
//        runBlocking {
//            newAccessToken = requestNewAccessToken()
//        }
//
//        return if (newAccessToken != null) {
//            preferenceManager.putAccessToken(newAccessToken ?: "")
//            val secondRequest = requestMaker(firstRequest)
//            chain.proceed(secondRequest)
//        } else {
//            response
//        }
//    }
//
//    private fun requestMaker(request: Request): Request {
//        return request.newBuilder()
//            .header("Authorization", preferenceManager.getAccessToken() ?: "")
//            .build()
//    }
//
//    private suspend fun requestNewAccessToken(): String? =
//        Retrofit.Builder()
//            .baseUrl("BuildConfig.BASE_URL")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(LoginDataSource::class.java).requestNewAccessToken(
//                accessToken = preferenceManager.getAccessToken(),
//                refreshToken = preferenceManager.getRefreshToken()
//            )?.headers()?.get("Authorization")


}
