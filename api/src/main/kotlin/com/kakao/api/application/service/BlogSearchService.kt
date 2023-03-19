package com.kakao.api.application.service

import com.kakao.api.domain.blog.kakao.client.KakaoClient
import com.kakao.api.domain.blog.naver.client.NaverClient

class BlogSearchService(
    private val kakaoClient: KakaoClient,
    private val naverClient: NaverClient,
) {

}