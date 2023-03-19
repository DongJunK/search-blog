package com.kakao.api.domain.blog.naver.model

import com.kakao.api.domain.blog.naver.enum.BlogSearchNaverSortType

data class BlogSearchNaverRequest(
    val keyword: String,
    val size: Int = 10,
    val page: Int = 1,
    val sortType: BlogSearchNaverSortType = BlogSearchNaverSortType.SIM,
)