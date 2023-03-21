package com.kakao.core.extension

import com.fasterxml.jackson.module.kotlin.convertValue
import com.kakao.core.configuration.objectMapper
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.exception.ClientException
import org.springframework.web.reactive.function.server.ServerRequest
import kotlin.reflect.full.memberProperties


inline fun <reified T : Any> ServerRequest.queryParamsToModel(): T {
    val requestMap: MutableMap<String, Any?> = mutableMapOf()
    T::class.memberProperties.forEach {
        if (this.queryParams()[it.name] != null) {
            requestMap[it.name] = this.queryParams()[it.name]?.firstOrNull()
        }
    }

    return try {
        objectMapper.readValue(objectMapper.writeValueAsString(requestMap), T::class.java)
    } catch (e: Exception){
        throw ClientException(ClientErrorCode.REQUEST_CONVERT_ERROR)
    }

}