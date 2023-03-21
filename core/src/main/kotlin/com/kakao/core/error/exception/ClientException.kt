package com.kakao.core.error.exception

import com.kakao.core.error.errorcode.ClientErrorCode

class ClientException(
    val errorCode: ClientErrorCode,
    override val message: String = errorCode.getMessage(),
) : RuntimeException(message)