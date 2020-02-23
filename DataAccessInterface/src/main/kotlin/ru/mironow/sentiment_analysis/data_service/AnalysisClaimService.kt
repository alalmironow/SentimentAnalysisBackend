package ru.mironow.sentiment_analysis.data_service

import ru.mironow.sentiment_analysis.AnalysisClaim

/**
 * Service for work with analysis claims
 *
 * Created By Alexander Mironow - 14.02.2020
 */
interface AnalysisClaimService {
    /**
     * Save analysis claim [claim]
     */
    fun save(claim: AnalysisClaim)

    /**
     * Update analysis claim [claim]
     */
    fun update(claim: AnalysisClaim)

    /**
     * Get analysis claim by [id]
     */
    fun getById(id: String): AnalysisClaim
}