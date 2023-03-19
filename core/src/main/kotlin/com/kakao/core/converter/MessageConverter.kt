package com.kakao.core.converter

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

class MessageConverter {
    companion object {
        private val messageSource: MessageSource = ResourceBundleMessageSource().apply {
            setBasenames("messages/error")
            setDefaultEncoding("UTF-8")
        }

        fun getMessage(code: String, args: Array<Any>? = null): String {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
        }
    }
}