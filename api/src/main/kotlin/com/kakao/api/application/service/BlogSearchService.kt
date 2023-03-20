package com.kakao.api.application.service

import com.kakao.api.application.service.model.BlogSearchRequest
import com.kakao.api.application.service.model.BlogSearchResponse
import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.naver.client.NaverClient
import com.kakao.core.error.exception.KakaoServerException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
    private val kakaoClient: KakaoClient,
    private val naverClient: NaverClient,
) {
    private val log = LoggerFactory.getLogger(javaClass)
    suspend fun searchBlog(request: BlogSearchRequest): BlogSearchResponse {
        return try {
            val response = kakaoClient.searchBlog(request.toKakaoRequest())

            BlogSearchResponse.createBy(response)
        } catch (e: KakaoServerException) {
            log.error("[KAKAO SERVER ERROR]errorCode = ${e.errorCode} message = ${e.message}")

            val response = naverClient.searchBlog(request.toNaverRequest())

            BlogSearchResponse.createBy(response)
        }
    }
}