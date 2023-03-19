package com.kakao.core.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("kakao")
data class KakaoProperties(
    val restApiKey: String = "",
)