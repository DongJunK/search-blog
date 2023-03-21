package com.kakao.core.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("kakao")
data class KakaoProperties(
    var restApiKey: String = "",
)