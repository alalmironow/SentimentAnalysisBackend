package ru.mironow.sentiment_analysis

/**
 * Claim for analysis user's opinions
 *
 * Created By Alexander Mironow - 13.02.2020
 */
data class AnalysisClaim(
        val id: String,
        val q: String,
        val stage: AnalysisClaimStage = AnalysisClaimStage.PENDING,
        val error: String? = null,
        val percent: Int = 0,
        val sentimentNegative: Boolean? = null
)