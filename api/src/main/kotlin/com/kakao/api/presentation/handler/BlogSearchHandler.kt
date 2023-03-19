package com.kakao.api.presentation.handler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class BlogSearchHandler {

    suspend fun searchBlog(serverRequest: ServerRequest): ServerResponse {
        return withContext(Dispatchers.Default) {


            ServerResponse.noContent().buildAndAwait()
        }
    }
}