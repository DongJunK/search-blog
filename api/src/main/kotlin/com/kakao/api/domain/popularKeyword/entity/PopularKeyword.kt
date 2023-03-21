package com.kakao.api.domain.popularKeyword.entity

import jakarta.persistence.*

@Entity
@Table(name = "popular_keyword")
data class PopularKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq: Int = 0,
    @Column(unique = true)
    val keyword: String = "",
    @Column(name = "search_count")
    var searchCount: Int = 0,
) {
    fun increaseSearchCount() {
        searchCount++
    }
}