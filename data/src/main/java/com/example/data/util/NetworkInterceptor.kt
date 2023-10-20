package com.example.data.util

import android.util.Log
import com.example.domain.exception.KeyExpiredException
import com.example.domain.exception.NonDataException
import com.example.domain.exception.ServerErrException
import com.example.domain.util.Constants
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * 2023-08-29
 * pureum
 */
class NetworkInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            return response.newBuilder().body(checkResponse(response)).build()
        } catch (e: Exception) {
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message(e.interceptorExceptionHandler())
                .body(("".toResponseBody(null)))
                .build()
        }
    }

    private fun Exception.interceptorExceptionHandler(): String {
        return when (this) {
            is SocketException -> Constants.SOCKET_EXCEPTION
            is HttpException -> Constants.HTTP_EXCEPTION
            is UnknownHostException -> Constants.UNKNOWN_HOST_EXCEPTION // 데이터 연결 실패 시
            is SocketTimeoutException -> Constants.SOCKET_TIMEOUT_EXCEPTION // 연결 시간 초과 시
            is IllegalStateException -> Constants.ILLEGAL_STATE_EXCEPTION
            is JSONException -> Constants.JSON_EXCEPTION
            is NonDataException -> Constants.NON_KEYWORD_EXCEPTION
            is ServerErrException -> Constants.SERVER_ERR_EXCEPTION
            is KeyExpiredException -> Constants.KEY_EXPIRED_EXCEPTION
            else -> Constants.UNHANDLED_EXCEPTION
        }
    }

    private fun checkResponse(response: Response): ResponseBody {
        val jsonResponse = JSONObject(response.body?.string() ?: "")
        if (jsonResponse.has("RESULT.CODE"))
            when(val httpExceptionCode = jsonResponse["RESULT.CODE"].toString()){
                Constants.INFO_200 -> throw NonDataException(httpExceptionCode)
                Constants.ERROR_500 -> throw ServerErrException(httpExceptionCode)
                Constants.INFO_100 -> throw KeyExpiredException(httpExceptionCode)
                else -> throw Exception(httpExceptionCode)
            }
        return removeOutLine(jsonResponse)
    }

    private fun removeOutLine(jsonObject: JSONObject): ResponseBody =
        JSONObject(JSONArray(jsonObject["SeoulRtd.citydata_ppltn"].toString())[0].toString()).toString()
            .toResponseBody()

//    private fun checkXml(inputStream: InputStream?): String {
//        try {
//            val parser: XmlPullParser = Xml.newPullParser()
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
//            parser.setInput(inputStream, null)
//            var eventType = parser.eventType
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if (eventType == XmlPullParser.START_TAG) {
//                    Log.e("TAG", "checkXml: ${parser.name}", )
//                    when(parser.name){
//                        "RESULT.CODE" -> return getNext(parser)
//                        "CODE" -> return getNext(parser)
//                        "SeoulRtd.citydata_ppltn" -> return "OK"
//                    }
//                }
//                parser.next()
//                eventType = parser.eventType  // 반드시 호출 전에 확인
//            }
//            return "ERR"
//        } catch (E: Exception) {
//            return "ERR"
//        }
//    }
//    private fun getNext(parser: XmlPullParser): String {
//        return when (parser.next()) {
//            XmlPullParser.TEXT -> parser.text
//            else -> ""
//        }
//    }
}
