package com.kakao.api.domain.blog.naver.client

import com.kakao.api.domain.blog.naver.model.NaverBlogSearchRequest
import com.kakao.api.domain.blog.naver.model.NaverBlogSearchResponse

interface NaverClient {
    suspend fun searchBlog(naverBlogSearchRequest: NaverBlogSearchRequest): NaverBlogSearchResponse
}