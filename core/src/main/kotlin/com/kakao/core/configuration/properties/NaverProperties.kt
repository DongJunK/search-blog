package com.kakao.core.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("naver")
data class NaverProperties(
    val clientId: String = "",
    val clientSecret: String = "",
)