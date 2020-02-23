package ru.mironow.sentiment_analysis.web.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mironow.sentiment_analysis.AnalysisResult

/**
 * Entity result of sentiment analysis
 *
 * REST level
 * Created By Alexander Mironow - 23.02.2020
 */
data class AnalysisResultRest private constructor(
        @JsonProperty("claim_id") val claimId: String,
        val statistics: List<StatisticDayRest>
) {
    constructor(result: AnalysisResult): this(
            claimId = result.claimId,
            statistics = result.statistics.map { StatisticDayRest(it) })
}