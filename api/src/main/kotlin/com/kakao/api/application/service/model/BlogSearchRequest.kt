package com.kakao.api.application.service.model

import com.kakao.api.application.service.enum.BlogSearchSortType
import com.kakao.api.domain.kakao.enum.BlogSearchKakaoSortType
import com.kakao.api.domain.kakao.model.KakaoBlogSearchRequest
import com.kakao.api.domain.naver.enum.BlogSearchNaverSortType
import com.kakao.api.domain.naver.model.NaverBlogSearchRequest
import com.kakao.core.error.errorcode.ClientErrorCode
import com.kakao.core.error.exception.ClientException

data class BlogSearchRequest(
    val blogUrl: String? = null,
    val keyword: String,
    val sortType: BlogSearchSortType = BlogSearchSortType.ACCURACY,
    val page: Int = 1,
    val size: Int = 10,
) {
    fun validate() {
        if (page < 1 || page > 50) {
            throw ClientException(ClientErrorCode.REQUEST_VALUE_ERROR)
        }

        if (size < 1 || size > 50) {
            throw ClientException(ClientErrorCode.REQUEST_VALUE_ERROR)
        }
    }

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