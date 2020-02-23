package ru.mironow.sentiment_analysis.data_service

import ru.mironow.sentiment_analysis.AnalysisResult

/**
 * Service for work with sentiment analysis result
 *
 * Created By Alexander Mironow - 23.02.2020
 */
interface AnalysisResultService {
    /**
     * Save analysis result in DB
     */
    fun save(analysisResult: AnalysisResult)

    /**
     * Get by claim [id]
     */
    fun getByClaimId(id: String): AnalysisResult
}