//package com.example.data.util
//
///**
// * 2023-10-16
// * pureum
// */
//
//import android.content.Context
//import android.util.Log
//import com.google.gson.JsonElement
//import com.google.gson.JsonObject
//import com.google.gson.JsonParser
//import com.kaii.dentii.libraries.remote.BuildConfig
//import com.kaii.dentii.libraries.remote.R
//import com.kaii.dentii.libraries.remote_contract.utils.AutoLoginRequest
//import com.kaii.dentii.libraries.remote_contract.utils.ErrorType
//import com.kaii.dentii.libraries.remote_contract.utils.ErrorTypeAnnotation
//import com.kaii.dentii.libraries.remote_contract.utils.NotAuthenticated
//import com.kaii.dentii.libraries.storage_contract.GGStorage
//import com.kaii.dentii.libraries.storage_contract.constants.StorageKeys
//import dagger.hilt.android.qualifiers.ApplicationContext
//import okhttp3.Interceptor
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.Request
//import okhttp3.RequestBody.Companion.toRequestBody
//import okhttp3.Response
//import okhttp3.ResponseBody
//import okio.Buffer
//import okio.BufferedSource
//import retrofit2.Invocation
//import retrofit2.http.GET
//import java.nio.charset.Charset
//import java.util.*
//import javax.inject.Inject
//
//
//class GGInterceptor @Inject constructor(
//    private val local: GGStorage,
//    @ApplicationContext private val context: Context
//): Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val buildRequest = request.newBuilder()
//        val invocation = request.tag(Invocation::class.java) ?: return chain.proceed(request)
//
//        val shouldAttachAuthHeader = invocation
//            .method()
//            .annotations
//            .any { it.annotationClass == NotAuthenticated::class }
//
//        val isAutoLogin = invocation
//            .method()
//            .annotations
//            .any { it.annotationClass == AutoLoginRequest::class }
//
//        val isErrorTypeAvailable = invocation
//            .method()
//            .annotations
//            .any { it.annotationClass == ErrorTypeAnnotation::class }
//
//        val response = if (shouldAttachAuthHeader) {
//            defaultRoutine(chain)
//        } else {
//            authenticatedRoutine(chain)
//        }
//
//        val responseBody = response.body ?: return response
//
//        responseBody.rawResponseBodyParsing()?.let { json ->
//            if (json.has("rt")) {
//                json["rt"]?.let { element ->
//                    when (element.asInt) {
//                        400 -> {
//                            if (!isErrorTypeAvailable) {
//                                throw BadRequestException(createErrorMessage(context, 400, false))
//                            }
//
//                            val annotation = invocation.method().getAnnotation(ErrorTypeAnnotation::class.java)
//                            if (annotation != null && annotation.type == ErrorType.ACCOUNT_REGIST) {
//                                throw BadRequestException(createErrorMessage(context, 400, true))
//                            }
//
//                            throw BadRequestException(createErrorMessage(context, 400, false))
//                        }
//                        401 -> {
//                            Log.e("TAG", "intercept: $isErrorTypeAvailable / $isAutoLogin")
//                            if (!isErrorTypeAvailable) {
//                                if (isAutoLogin) {
//                                    throw AutoLoginAuthenticationFailException(createErrorMessage(context, 401, false))
//                                } else {
//                                    throw AuthenticationFailException(createErrorMessage(context, 401, false))
//                                }
//                            }
//                            val annotation = invocation.method().getAnnotation(ErrorTypeAnnotation::class.java)
//                            Log.e("TAG", "intercept: $annotation")
//                            if (annotation != null && annotation.type == ErrorType.ACCOUNT_REGIST) {
//                                if (isAutoLogin) {
//                                    throw AutoLoginAuthenticationFailException(createErrorMessage(context, 401, true))
//                                } else {
//                                    throw AuthenticationFailException(createErrorMessage(context, 401, true))
//                                }
//                            }
//                            throw AuthenticationFailException(createErrorMessage(context, 401, false))
//
//                        }
//                        403 -> {
//                            // access token 초기화 요청 >> refresh token 만료 시 재 로그인.
//                            return refreshTokenRoutine(chain, buildRequest)
//                        }
//
//                        417 -> {
//                            if (!isErrorTypeAvailable) {
//                                throw DataValidateFailException(createErrorMessage(context, 417, false))
//                            }
//
//                            val annotation = invocation.method().getAnnotation(ErrorTypeAnnotation::class.java)
//
//                            if (annotation != null && annotation.type == ErrorType.ACCOUNT_REGIST) {
//                                throw DataValidateFailException(createErrorMessage(context, 417, true))
//                            }
//
//                            throw DataValidateFailException(createErrorMessage(context, 417, false))
//                        }
//
//                        422 -> {
//                            if (!isErrorTypeAvailable) {
//                                throw DataNotFoundException(createErrorMessage(context, 422, false))
//                            }
//
//                            val annotation = invocation.method().getAnnotation(ErrorTypeAnnotation::class.java)
//
//                            if (annotation != null && annotation.type == ErrorType.ACCOUNT_REGIST) {
//                                throw DataNotFoundException(createErrorMessage(context, 422, true))
//                            }
//
//                            throw DataNotFoundException(createErrorMessage(context, 422, false))
//
//                        }
//
//                        500, 501, 502, 503 -> {
//                            throw UnknownErrorException(createErrorMessage(context, 500, false))
//                        }
//                        else -> {
//                            return@let
//                        }
//                    }
//                }
//            } else { // json.has()
//                throw UnknownErrorException(createErrorMessage(context, 500, false))
//            }
//        }
//
//        return response
//    }
//
//    private fun refreshTokenRoutine(chain: Interceptor.Chain, request: Request.Builder): Response {
//
//        val newRequest = request
//            .removeHeader(BuildConfig.AUTHORIZATION)
//            .addHeader(BuildConfig.REFRESH_TOKEN, local.get<String>(StorageKeys.STORAGE_KEY_REFRESH_TOKEN) ?: "")
//            .url(BuildConfig.BASE_URL + BuildConfig.RE_ACCESS_TOKEN_URL)
//            .method("PUT", "".toRequestBody("application/json".toMediaType()))
//            .build()
//
//        chain.proceed(newRequest).also { response ->
//            response.body?.rawResponseBodyParsing()?.let { newResponseBody ->
//                when (newResponseBody["rt"].asInt) {
//                    200 -> {
//                        val newAccessToken = newResponseBody.get("accessToken") ?: throw UnknownErrorException(createErrorMessage(context, 500, false))
////                        local.setUserAccessToken(newAccessToken.asString)
//                        local.save(StorageKeys.STORAGE_KEY_ACCESS_TOKEN, newAccessToken.asString)
//
//                        val refreshRequest = chain.request().newBuilder()
//                            .addHeader(BuildConfig.AUTHORIZATION, newAccessToken.asString)
//
//                        return chain.proceed(refreshRequest.build())
//                    }
//
//                    else -> {
////                        local.clearPref()
//                        local.clear()
//                        throw RefreshTokenIsExpireException(createErrorMessage(context, 403, false))
//                    }
//                }
//            }
//        }
//        throw UnknownErrorException(createErrorMessage(context, 500, false))
//    }
//
//    private fun authenticatedRoutine(chain: Interceptor.Chain): Response {
//        val buildRequest = chain.request().newBuilder()
//        buildRequest.addHeader(BuildConfig.AUTHORIZATION, local.get<String>(StorageKeys.STORAGE_KEY_ACCESS_TOKEN) ?: "")
//        return chain.proceed(buildRequest.build())
//    }
//
//    private fun defaultRoutine(chain: Interceptor.Chain): Response {
//        return chain.proceed(chain.request())
//    }
//
//    private fun createErrorMessage(context: Context, errorCode: Int, isTypeAccountRegistration: Boolean): String {
//        val plzQaMessage = context.getString(R.string.txt_error_general_desc)
//        var message = ""
//
//        return when(errorCode) {
//            400 -> {
//                message = if (isTypeAccountRegistration) context.getString(R.string.txt_error_login_not_found)
//                else context.getString(R.string.txt_error_bad_request)
//                message += "\n\n" + plzQaMessage
//                message
//            }
//
//            401 -> {
//                message = if (isTypeAccountRegistration) context.getString( R.string.txt_error_auth_not_mapping)
//                else context.getString(R.string.txt_error_authorization)
//                message += "\n" + "\n" + plzQaMessage
//                message
//            }
//
//            403 -> {
//                message = context.getString(R.string.txt_error_permission_expire)
//                message
//            }
//
//            417 -> {
//                message = if (isTypeAccountRegistration) context.getString( R.string.txt_error_auth_aleady_join)
//                else context.getString(R.string.txt_error_not_valid)
//                message += "\n\n" + plzQaMessage
//                message
//            }
//
//            422 -> {
//                message = if (isTypeAccountRegistration) context.getString( R.string.txt_error_auth_duplicated)
//                else context.getString(R.string.txt_error_read_fail)
//                message += "\n\n" + plzQaMessage
//                message
//            }
//
//            else -> {
//                message = context.getString(R.string.txt_error_Uncaught) + "\n\n" + plzQaMessage
//                message
//            }
//        }
//    }
//
//}
//
//fun ResponseBody.rawResponseBodyParsing(): JsonObject? {
//    contentType()?.subtype?.let {
//        if (it.lowercase(Locale.getDefault()) == "json") {
//            val source: BufferedSource = source()
//            source.request(Long.MAX_VALUE)
//
//            val buffer: Buffer = source.buffer
//            val charset: Charset? = contentType()?.charset(Charset.forName("UTF-8"))
//            charset?.let { c ->
//                val json: String = buffer.clone().readString(c)
////                val obj: JsonElement = JsonParser().parse(json)
//                val obj: JsonElement = JsonParser.parseString(json)
//
//                return obj.asJsonObject
//            }
//        }
//    }
//
//    return null
//}
//
//
//import retrofit2.http.QueryMap
//
///**
// * 2023-10-16
// * pureum
// */
//
//// 어노테이션
//@Target(AnnotationTarget.FUNCTION)
//annotation class AutoLoginRequest
//
//@Target(AnnotationTarget.FUNCTION)
//annotation class NotAuthenticated
//
//@Target(AnnotationTarget.FUNCTION)
//annotation class VersionCheck
//
//@Target(AnnotationTarget.FUNCTION)
//annotation class ErrorTypeAnnotation(val type: ErrorType)
//
//
////어노테이션 활용하는거
//@GET("/app-version/gyeonggi-check")
//@NotAuthenticated
//suspend fun checkVersion(@QueryMap map: HashMap<String, String>): VersionCheckResponse
//
//
//
//// 이게 이제 api 호출하는 부분
//solveQuizUseCase.invoke(createParams())
//    .onStart { setLoadingState(true) }
//    .onEach { message ->
//        setState(QuizState.ShowResultPopup(
//            message = message,
//            isSuccess = message != "조금만 더 힘내요!"
//        ))
//    }.catch { throwable ->
//        setError(throwable)
//    }.onCompletion { setLoadingState(false) }
//    .launchIn(viewModelScope)
//
//
//
//
//// setError 함수
//fun setError(t: Throwable) {
//    sendEvent(UiEvent.Error(
//        throwable = t,
//        message = t.localizedMessage.toString()
//    ))
//}
//
//
//
////setError에서 생긴 throwable 처리하는 함수
//collectLatestLifecycleFlow(flow = viewModel.error) { error ->
//    val item = if (error.throwable is RefreshTokenIsExpireException) {
//        DefaultDialogItem(
//            isVisibleCloseButton = false,
//            message = error.message,
//            actionPositiveButton = {
//                NavHostController(this).safeNavigate(NavGraphDirections.globalGoLoginFragment())
//            }
//        )
//    } else {
//        DefaultDialogItem(
//            isVisibleCloseButton = false,
//            message = error.message,
//            actionPositiveButton = {
//                error.afterEvent?.invoke()
//            }
//        )
//    }
//    navController.navigate(R.id.global_default_dialog_show, bundleOf("item" to item))
//}