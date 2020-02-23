package ru.mironow.sentiment_analysis

/**
 * Business entity result of sentiment analysis
 *
 * Created By Alexander Mironow - 23.02.2020
 */
data class AnalysisResult(
        val claimId: String,
        val statistics: List<StatisticDay>
)