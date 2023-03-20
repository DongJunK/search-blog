package com.kakao.api.domain.blog.popularKeyword.service

import com.kakao.api.domain.blog.popularKeyword.entity.PopularKeyword
import com.kakao.api.domain.blog.popularKeyword.repository.PopularKeywordRepository
import com.kakao.api.domain.blog.popularKeyword.service.model.PopularKeywordDomainResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PopularKeywordDomainService(
    private val popularKeywordRepository: PopularKeywordRepository,
) {
    @Transactional
    fun increaseSearchCount(keyword: String) {
        val popularKeyword = popularKeywordRepository.findByKeyword(keyword)

        popularKeyword?.increaseSearchCount() ?: run {
            popularKeywordRepository.save(
                PopularKeyword(
                    keyword = keyword,
                    searchCount = 1
                )
            )
        }
    }

    @Transactional(readOnly = true)
    fun getPopularKeywords(): List<PopularKeywordDomainResponse> {
        return popularKeywordRepository.getPopularKeywords()?.map {
            PopularKeywordDomainResponse.createBy(it)
        } ?: emptyList()
    }
}