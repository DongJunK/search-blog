package com.kakao.api.domain.blog.kakao.model

import com.kakao.api.domain.blog.kakao.enum.BlogSearchKakaoSortType

data class BlogSearchKakaoRequest(
    val blogUrl: String? = null,
    val keyword: String,
    val sortType: BlogSearchKakaoSortType,
    val page: Int = 1,
    val size: Int = 10,
)

