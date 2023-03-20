package com.kakao.api.infrastructure.webclient

import com.kakao.api.domain.blog.naver.client.NaverClient
import com.kakao.core.error.exception.NaverServerException
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverRequest
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverResponse
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NaverClientImpl(
    private val naverWebClient: WebClient,
) : NaverClient {
    override suspend fun searchBlog(blogSearchNaverRequest: BlogSearchNaverRequest): BlogSearchNaverResponse {
        return withContext(Dispatchers.IO) {
            naverWebClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path(SEARCH_BLOG_API_URL)
                        .queryParam("query", blogSearchNaverRequest.keyword)
                        .queryParam("sort", blogSearchNaverRequest.sortType.value)
                        .queryParam("page", blogSearchNaverRequest.page)
                        .queryParam("size", blogSearchNaverRequest.size)
                        .build()
                }
                .retrieve()
                .onStatus({ status -> status.is4xxClientError }) {
                    throw ClientException(ClientErrorCode.REQUEST_ERROR)
                }
                .onStatus({ status -> status.is5xxServerError }) {
                    throw NaverServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)
                }
                .bodyToMono(BlogSearchNaverResponse::class.java)
                .block()
                ?: BlogSearchNaverResponse.empty()
        }
    }

    companion object {
        const val SEARCH_BLOG_API_URL = "/v1/search/blog.json"
    }
}