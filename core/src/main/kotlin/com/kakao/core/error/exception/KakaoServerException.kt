package com.kakao.core.error.exception

import com.kakao.core.error.errorcode.ServerErrorCode

class KakaoServerException(
    val errorCode: ServerErrorCode,
    override val message: String = errorCode.getMessage(),
) : RuntimeException(message)