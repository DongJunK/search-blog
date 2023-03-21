package com.kakao.api.domain.naver.client

import com.kakao.api.domain.naver.model.NaverBlogSearchRequest
import com.kakao.api.domain.naver.model.NaverBlogSearchResponse

interface NaverClient {
    suspend fun searchBlog(naverBlogSearchRequest: NaverBlogSearchRequest): NaverBlogSearchResponse
}