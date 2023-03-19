package com.kakao.core.error.errorcode

import com.kakao.core.converter.MessageConverter
import org.springframework.http.HttpStatus

enum class ClientErrorCode(
    val status: HttpStatus,
    val code: String,
) : ErrorCode {
    REQUEST_ERROR(HttpStatus.BAD_REQUEST, "E0001"),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E0002"),
    ;

    override fun getMessage() = MessageConverter.getMessage("error.properties.$code")
}