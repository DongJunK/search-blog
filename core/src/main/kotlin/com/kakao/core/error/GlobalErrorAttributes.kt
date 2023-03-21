package com.kakao.core.error

import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import com.kakao.core.error.exception.NaverServerException
import com.kakao.core.error.exception.ServerException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.ResponseStatusException
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val error = this.getError(request)

        val errorAttributeResponse = when (error) {
            is ServerException -> handleServerException(error)
            is ClientException -> handleClientException(error)
            is NaverServerException -> handleNaverServerException(error)
            is ResponseStatusException -> handleResponseStatusException(error)
            else -> handleUnknownException(error)
        }

        return mutableMapOf(
            "timestamp" to LocalDateTime.now().toString(),
            "status" to errorAttributeResponse.status.value(),
            "message" to errorAttributeResponse.message,
            "path" to request.uri().path
        )
    }

    fun handleClientException(ex: ClientException): ErrorAttributeResponse {
        log.info(getStackTrace(ex))

        return ErrorAttributeResponse(
            status = ex.errorCode.status,
            message = ex.errorCode.getMessage()
        )
    }

    fun handleResponseStatusException(ex: ResponseStatusException): ErrorAttributeResponse {
        return if (ex.statusCode.isSameCodeAs(HttpStatus.NOT_FOUND)) {
            ErrorAttributeResponse(
                status = HttpStatus.NOT_FOUND,
                message = ClientErrorCode.NOT_FOUND_ERROR.getMessage()
            )
        } else {
            ErrorAttributeResponse(
                status = HttpStatus.BAD_REQUEST,
                message = ClientErrorCode.REQUEST_ERROR.getMessage()
            )
        }
    }

    fun handleServerException(ex: ServerException): ErrorAttributeResponse {
        log.error(getStackTrace(ex))

        return ErrorAttributeResponse(
            status = ex.errorCode.status,
            message = ex.errorCode.getMessage()
        )
    }

    fun handleNaverServerException(ex: NaverServerException): ErrorAttributeResponse {
        log.error("[NAVER SERVER EXCEPTION] ${getStackTrace(ex)}")

        return ErrorAttributeResponse(
            status = ex.errorCode.status,
            message = ex.errorCode.getMessage()
        )
    }

    fun handleUnknownException(ex: Throwable): ErrorAttributeResponse {
        log.error(getStackTrace(ex))

        return ErrorAttributeResponse(
            status = ServerErrorCode.INTERNAL_SERVER_ERROR.status,
            message = ServerErrorCode.INTERNAL_SERVER_ERROR.getMessage()
        )
    }

    private fun getStackTrace(error: Throwable?): String? {
        return error?.let {
            val stackTrace = StringWriter()
            error.printStackTrace(PrintWriter(stackTrace))
            stackTrace.toString()
        }
    }

    class ErrorAttributeResponse(
        val status: HttpStatus,
        val message: String = "",
    )
}