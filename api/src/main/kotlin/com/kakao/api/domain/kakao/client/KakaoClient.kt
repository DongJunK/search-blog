package com.kakao.api.domain.kakao.client

import com.kakao.api.domain.kakao.model.KakaoBlogSearchRequest
import com.kakao.api.domain.kakao.model.KakaoBlogSearchResponse


interface KakaoClient {
    suspend fun searchBlog(kakaoBlogSearchRequest: KakaoBlogSearchRequest): KakaoBlogSearchResponse
}