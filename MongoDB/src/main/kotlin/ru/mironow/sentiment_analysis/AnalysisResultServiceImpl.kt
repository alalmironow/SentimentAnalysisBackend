package ru.mironow.sentiment_analysis

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.data_service.AnalysisResultService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Implementation service for work with analysis result
 *
 * Created By Alexander Mironow - 23.02.2020
 */
@Service
open class AnalysisResultServiceImpl(val database: DB) : AnalysisResultService {
    override fun save(analysisResult: AnalysisResult) {
        val dbObject = analysisResult.toDBObject()
        database.getCollection(COLLECTION_NAME).save(dbObject)
    }

    override fun getByClaimId(id: String) = database.getCollection(COLLECTION_NAME)
            .find(BasicDBObject("claim_id", id))
            .next()
            .toEntity()

    companion object {
        private const val COLLECTION_NAME = "analysis_result"

        fun AnalysisResult.toDBObject() = BasicDBObject()
                .append("claim_id", claimId)
                .append("statistics", statistics.map { it.toDBObject() })

        fun DBObject.toEntity() = AnalysisResult(
                claimId = get("claim_id") as String,
                statistics = (get("statistics") as List<DBObject>).map { it.toEntity1() }
        )

        fun DBObject.toEntity1() = StatisticDay(
                date =  LocalDate.parse(get("date") as String, DateTimeFormatter.ISO_DATE),
                countPositive = get("count_positive") as Int,
                countNegative = get("count_negative") as Int,
                count = get("count") as Int,
                statisticMessage = get("statistic_message") as String
        )

        fun StatisticDay.toDBObject() = BasicDBObject()
                .append("date", date.format(DateTimeFormatter.ISO_DATE))
                .append("count_positive", countPositive)
                .append("count_negative", countNegative)
                .append("count", count)
                .append("statistic_message", statisticMessage)
    }
}