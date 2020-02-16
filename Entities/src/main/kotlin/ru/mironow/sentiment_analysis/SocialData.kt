package ru.mironow.sentiment_analysis

import java.time.LocalDateTime

/**
 * Data from social networks
 * (posts, comments, messages, tweets)
 */
data class SocialData(
        val data: String,
        val source: SocialDataSource,
        val type: SocialDataType,
        val date: LocalDateTime
)

enum class SocialDataSource(vararg types: SocialDataType) {
    VK //VK
}

enum class SocialDataType {
    POST,
    COMMENT
}