package com.kakao.api.infrastructure.webclient

import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchRequest
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchResponse
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.errorcode.ServerErrorCode
import com.kakao.core.error.exception.ClientException
import com.kakao.core.error.exception.KakaoServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoClientImpl(
    private val kakaoWebClient: WebClient,
) : KakaoClient {

    override suspend fun searchBlog(kakaoBlogSearchRequest: KakaoBlogSearchRequest): KakaoBlogSearchResponse {
        return withContext(Dispatchers.IO) {
            kakaoWebClient.get()
                .uri { uriBuilder ->
                    uriBuilder.path(SEARCH_BLOG_API_URL)
                        .queryParam(
                            "query",
                            if (kakaoBlogSearchRequest.blogUrl.isNullOrEmpty()) {
                                kakaoBlogSearchRequest.keyword
                            } else {
                                "${kakaoBlogSearchRequest.blogUrl} ${kakaoBlogSearchRequest.keyword}"
                            }
                        )
                        .queryParam("sort", kakaoBlogSearchRequest.sortType.value)
                        .queryParam("page", kakaoBlogSearchRequest.page)
                        .queryParam("size", kakaoBlogSearchRequest.size)
                        .build()
                }
                .retrieve()
                .onStatus({ status -> status.is4xxClientError }) {
                    throw ClientException(ClientErrorCode.REQUEST_ERROR)
                }
                .onStatus({ status -> status.is5xxServerError }) {
                    throw KakaoServerException(ServerErrorCode.INTERNAL_SERVER_ERROR)
                }
                .bodyToMono(KakaoBlogSearchResponse::class.java)
                .block()
                ?: KakaoBlogSearchResponse.empty()
        }

    }

    companion object {
        const val SEARCH_BLOG_API_URL = "/v2/search/blog"
    }
}