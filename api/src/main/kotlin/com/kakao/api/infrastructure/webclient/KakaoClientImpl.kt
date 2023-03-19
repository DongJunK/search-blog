package com.kakao.api.infrastructure.webclient

import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoRequest
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoClientImpl(
    private val kakaoWebClient: WebClient,
) : KakaoClient {
    override fun searchBlog(blogSearchKakaoRequest: BlogSearchKakaoRequest): BlogSearchKakaoResponse {
        return kakaoWebClient.get()
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
            .bodyToMono(BlogSearchKakaoResponse::class.java)
            .block()
            ?: BlogSearchKakaoResponse.empty()
    }

    companion object {
        const val SEARCH_BLOG_API_URL = "/v2/search/blog"
    }
}