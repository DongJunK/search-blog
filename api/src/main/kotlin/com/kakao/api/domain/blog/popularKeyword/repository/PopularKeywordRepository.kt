package com.kakao.api.domain.blog.popularKeyword.repository

import com.kakao.api.domain.blog.popularKeyword.entity.PopularKeyword
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface PopularKeywordRepository : JpaRepository<PopularKeyword, Int> {
    @Lock(value = LockModeType.OPTIMISTIC)
    fun findByKeyword(keyword: String): PopularKeyword?

    @Query("select * from popular_keyword order by search_count desc limit 10", nativeQuery = true)
    fun getPopularKeywords(): List<PopularKeyword>?
}