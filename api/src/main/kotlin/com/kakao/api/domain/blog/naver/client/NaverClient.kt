package com.kakao.api.domain.blog.naver.client

import com.kakao.api.domain.blog.naver.model.BlogSearchNaverRequest
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverResponse

interface NaverClient {
    fun searchBlog(blogSearchNaverRequest: BlogSearchNaverRequest): BlogSearchNaverResponse
}