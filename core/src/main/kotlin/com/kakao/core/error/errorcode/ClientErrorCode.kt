package com.kakao.core.error.errorcode

import com.kakao.core.converter.MessageConverter
import org.springframework.http.HttpStatus

enum class ClientErrorCode(
    val status: HttpStatus,
    val code: String,
) : ErrorCode {
    REQUEST_ERROR(HttpStatus.BAD_REQUEST, "EC0001"),
    REQUEST_VALUE_ERROR(HttpStatus.BAD_REQUEST, "EC0002"),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "EC0003"),
    ;

    override fun getMessage() = MessageConverter.getMessage("error.properties.$code")
}