package com.kakao.api.domain.blog.kakao.client

import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoRequest
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoResponse


interface KakaoClient {
    suspend fun searchBlog(blogSearchKakaoRequest: BlogSearchKakaoRequest): BlogSearchKakaoResponse
}