package com.kakao.api.domain.naver.model

import com.kakao.api.domain.naver.enum.BlogSearchNaverSortType

data class NaverBlogSearchRequest(
    val keyword: String,
    val size: Int = 10,
    val page: Int = 1,
    val sortType: BlogSearchNaverSortType = BlogSearchNaverSortType.SIM,
)