package com.kakao.api.application.service.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kakao.api.application.service.enum.BlogSearchSortType
import com.kakao.api.domain.blog.kakao.enum.BlogSearchKakaoSortType
import com.kakao.api.domain.blog.kakao.model.KakaoBlogSearchRequest
import com.kakao.api.domain.blog.naver.enum.BlogSearchNaverSortType
import com.kakao.api.domain.blog.naver.model.NaverBlogSearchRequest

data class BlogSearchRequest(
    val blogUrl: String? = null,
    val keyword: String,
    val sortType: BlogSearchSortType = BlogSearchSortType.ACCURACY,
    val page: Int = 1,
    val size: Int = 10,
) {
    fun toKakaoRequest(): KakaoBlogSearchRequest {
        return KakaoBlogSearchRequest(
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

    fun toNaverRequest(): NaverBlogSearchRequest {
        return NaverBlogSearchRequest(
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