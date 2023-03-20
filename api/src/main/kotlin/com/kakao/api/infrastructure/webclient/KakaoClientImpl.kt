package com.kakao.api.infrastructure.webclient

import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoRequest
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoResponse
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import com.kakao.core.error.exception.KakaoServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoClientImpl(
    private val kakaoWebClient: WebClient,
) : KakaoClient {

    override suspend fun searchBlog(blogSearchKakaoRequest: BlogSearchKakaoRequest): BlogSearchKakaoResponse {
        return withContext(Dispatchers.IO) {
            kakaoWebClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path(SEARCH_BLOG_API_URL)
                        .queryParam(
                            "query",
                            if (blogSearchKakaoRequest.blogUrl.isNullOrEmpty()) {
                                blogSearchKakaoRequest.keyword
                            } else {
                                "${blogSearchKakaoRequest.blogUrl} ${blogSearchKakaoRequest.keyword}"
                            }
                        )
                        .queryParam("sort", blogSearchKakaoRequest.sortType.value)
                        .queryParam("page", blogSearchKakaoRequest.page)
                        .queryParam("size", blogSearchKakaoRequest.size)
                        .build()
                }
                .retrieve()
                .onStatus({ status -> status.is4xxClientError }) {
                    throw ClientException(ClientErrorCode.REQUEST_ERROR)
                }
                .onStatus({ status -> status.is5xxServerError }) {
                    throw KakaoServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)
                }
                .bodyToMono(BlogSearchKakaoResponse::class.java)
                .block()
                ?: BlogSearchKakaoResponse.empty()
        }

    }

    companion object {
        const val SEARCH_BLOG_API_URL = "/v2/search/blog"
    }
}