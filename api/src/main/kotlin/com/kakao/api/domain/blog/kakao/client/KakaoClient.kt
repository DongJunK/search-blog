package com.kakao.api.domain.blog.kakao.client

import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchRequest
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchResponse


interface KakaoClient {
    suspend fun searchBlog(kakaoBlogSearchRequest: KakaoBlogSearchRequest): KakaoBlogSearchResponse
}