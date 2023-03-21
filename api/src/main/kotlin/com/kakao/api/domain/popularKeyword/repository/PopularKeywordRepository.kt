package com.kakao.api.domain.popularKeyword.repository

import com.kakao.api.domain.popularKeyword.entity.PopularKeyword
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock

interface PopularKeywordRepository : JpaRepository<PopularKeyword, Int> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun findByKeyword(keyword: String): PopularKeyword?


    fun findPopularKeywordBy(pageable: Pageable): List<PopularKeyword>
}