package ru.mironow.sentiment_analysis.web.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.mironow.sentiment_analysis.StatisticDay
import java.time.format.DateTimeFormatter

/**
 * Entity statistic day
 *
 * REST level
 * Created By Alexander Mironow - 23.02.2020
 */
data class StatisticDayRest(
        val date: String,
        @JsonProperty("count_positive") val countPositive: Int,
        @JsonProperty("count_negative") val countNegative: Int,
        @JsonProperty("count") val count: Int,
        @JsonProperty("statistic_message") val statisticMessage: String?
) {
    constructor(statisticDay: StatisticDay): this(
            date = statisticDay.date.format(DateTimeFormatter.ISO_DATE),
            countPositive = statisticDay.countPositive,
            countNegative = statisticDay.countNegative,
            count = statisticDay.count,
            statisticMessage = statisticDay.statisticMessage
    )
}