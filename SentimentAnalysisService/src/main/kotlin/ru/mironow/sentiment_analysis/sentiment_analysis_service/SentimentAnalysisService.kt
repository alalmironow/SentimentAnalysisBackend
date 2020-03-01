package ru.mironow.sentiment_analysis.sentiment_analysis_service

import org.springframework.stereotype.Service

/**
 * Service for analysis sentiment
 *
 * Created By Alexander Mironow - 18.02.2020
 */
@Service
class SentimentAnalysisService(val restApi: RestApi) {

    /**
     * Analysis Sentiment [text]
     * true - positive
     * false - negative
     */
    fun analysisSentiment(text: String): Boolean {
        val analysisRequest = AnalysisRequest(message = text)
        val body =  restApi.analysisSentiment(analysisRequest)
                .execute()
        return body.body().equals("1")
    }
}