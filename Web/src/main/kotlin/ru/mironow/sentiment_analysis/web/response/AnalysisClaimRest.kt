package ru.mironow.sentiment_analysis.web.response

import ru.mironow.sentiment_analysis.AnalysisClaim

/**
 * REST response on create claim
 *
 * Created By Alexander Mironow - 13.02.2020
 */
data class AnalysisClaimRest private constructor(val id: String) {
    constructor(claim: AnalysisClaim): this(id = claim.id)
}