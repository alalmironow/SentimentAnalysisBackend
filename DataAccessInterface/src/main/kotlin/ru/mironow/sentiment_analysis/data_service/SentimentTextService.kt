package ru.mironow.sentiment_analysis.data_service

import ru.mironow.sentiment_analysis.SentimentText

/**
 * Service for save text with defined sentiment
 *
 * Created By Alexander Mironow - 18.02.2020
 */
interface SentimentTextService {
    /**
     * Save sentiment text
     */
    fun save(sentimentText: SentimentText)
}