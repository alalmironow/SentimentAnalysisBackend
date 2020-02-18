package ru.mironow.sentiment_analysis

/**
 * Entity sentiment text
 *
 * Created By Alexander Mironow - 18.02.2020
 */
data class SentimentText(
        val text: String,
        val sentiment: Boolean
)