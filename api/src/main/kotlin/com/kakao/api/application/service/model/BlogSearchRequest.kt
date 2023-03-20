package com.kakao.api.application.service.model

import com.kakao.api.application.service.enum.BlogSearchSortType
import com.kakao.api.domain.blog.kakao.enum.BlogSearchKakaoSortType
import com.kakao.api.domain.blog.kakao.model.BlogSearchKakaoRequest
import com.kakao.api.domain.blog.naver.enum.BlogSearchNaverSortType
import com.kakao.api.domain.blog.naver.model.BlogSearchNaverRequest

data class BlogSearchRequest(
    val blogUrl: String? = null,
    val keyword: String,
    val sortType: BlogSearchSortType = BlogSearchSortType.ACCURACY,
    val page: Int = 1,
    val size: Int = 10,
) {
    fun toKakaoRequest(): BlogSearchKakaoRequest {
        return BlogSearchKakaoRequest(
            blogUrl = blogUrl,
            keyword = keyword,
            sortType = when (sortType) {
                BlogSearchSortType.ACCURACY -> BlogSearchKakaoSortType.ACCURACY
                BlogSearchSortType.RECENCY -> BlogSearchKakaoSortType.RECENCY
            },
            page = page,
            size = size
        )
    }

    fun toNaverRequest(): BlogSearchNaverRequest {
        return BlogSearchNaverRequest(
            keyword = keyword,
            sortType = when (sortType) {
                BlogSearchSortType.ACCURACY -> BlogSearchNaverSortType.SIM
                BlogSearchSortType.RECENCY -> BlogSearchNaverSortType.DATE
            },
            page = page,
            size = size
        )
    }
}