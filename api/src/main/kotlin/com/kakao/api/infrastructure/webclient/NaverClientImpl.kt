package com.kakao.api.infrastructure.webclient

import com.kakao.api.domain.blog.naver.client.NaverClient
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverRequest
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NaverClientImpl(
    private val naverWebClient: WebClient,
) : NaverClient {
    override fun searchBlog(blogSearchNaverRequest: BlogSearchNaverRequest): BlogSearchNaverResponse {
        return naverWebClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(SEARCH_BLOG_API_URL)
                    .queryParam("query", blogSearchNaverRequest.keyword)
                    .queryParam("sort", blogSearchNaverRequest.sortType.value)
                    .queryParam("page", blogSearchNaverRequest.page)
                    .queryParam("size", blogSearchNaverRequest.size)
                    .build()
            }
            .retrieve()
            .bodyToMono(BlogSearchNaverResponse::class.java)
            .block()
            ?: BlogSearchNaverResponse.empty()
    }

    companion object {
        const val SEARCH_BLOG_API_URL = "/v1/search/blog.json"
    }
}