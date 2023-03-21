package com.kakao.api.domain.popularKeyword.service

import com.kakao.api.domain.popularKeyword.entity.PopularKeyword
import com.kakao.api.domain.popularKeyword.repository.PopularKeywordRepository
import com.kakao.api.domain.popularKeyword.service.model.PopularKeywordDomainResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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

    fun selectPopularKeyword(limit: Int): List<PopularKeywordDomainResponse> {

        return popularKeywordRepository.findPopularKeywordBy(
            PageRequest.of(0, limit, Sort.by(PopularKeyword::searchCount.name).descending())
        ).map {
            PopularKeywordDomainResponse(
                keyword = it.keyword,
                searchCount = it.searchCount
            )
        }
    }

}