package com.kakao.api.domain.kakao.model

import com.kakao.api.domain.kakao.enum.BlogSearchKakaoSortType

data class KakaoBlogSearchRequest(
    val blogUrl: String? = null,
    val keyword: String,
    val sortType: BlogSearchKakaoSortType = BlogSearchKakaoSortType.ACCURACY,
    val page: Int = 1,
    val size: Int = 10,
)

