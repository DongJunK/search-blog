package com.kakao.core.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("naver")
data class NaverProperties(
    var clientId: String = "",
    var clientSecret: String = "",
)