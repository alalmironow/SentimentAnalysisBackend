package ru.mironow.sentiment_analysis.web.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mironow.sentiment_analysis.AnalysisClaim

/**
 * Ответ на создание заявки
 * РЕСТ представление
 *
 * Created By Alexander Mironow - 13.02.2020
 */
data class AnalysisClaimRest(
        @JsonProperty("unique_key") val uniqueKey: String
) {
    companion object {
        fun fromEntity(entity: AnalysisClaim) =  AnalysisClaimRest(
                uniqueKey = entity.uniqueKey
        )
    }
}