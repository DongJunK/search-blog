package com.kakao.api.presentation.handler

import com.kakao.api.application.service.BlogService
import com.kakao.api.application.service.model.BlogSearchRequest
import com.kakao.core.extension.queryParamsToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class BlogHandler(
    private val blogService: BlogService,
) {
    suspend fun searchBlog(serverRequest: ServerRequest): ServerResponse {
        val request = serverRequest.queryParamsToModel<BlogSearchRequest>().also {
            it.validate()
        }

        return withContext(Dispatchers.Default) {
            ServerResponse.ok().bodyValueAndAwait(
                blogService.searchBlog(request)
            )
        }
    }

    suspend fun getPopularKeywords(serverRequest: ServerRequest): ServerResponse {
        return withContext(Dispatchers.Default) {
            ServerResponse.ok().bodyValueAndAwait(
                blogService.getPopularKeywords()
            )
        }
    }
}