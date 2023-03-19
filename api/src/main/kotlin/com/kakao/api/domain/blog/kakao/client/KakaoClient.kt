package com.kakao.api.domain.blog.kakao.client

import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoRequest
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoResponse


interface KakaoClient {
    fun searchBlog(blogSearchKakaoRequest: BlogSearchKakaoRequest): BlogSearchKakaoResponse
}