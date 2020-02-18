package ru.mironow.sentiment_analysis

import com.mongodb.BasicDBObject
import com.mongodb.DB
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.data_service.AnalysisClaimService

/**
 * MongoDB service for work with analysis claims
 *
 * Created By Alexander Mironow - 14.02.2020
 */
@Service
class AnalysisClaimServiceImpl(val database: DB): AnalysisClaimService {
    override fun save(claim: AnalysisClaim) {
        val dbObject = claim.toDBObject()
        database.getCollection(COLLECTION_NAME).insert(dbObject)
    }

    companion object {
        const val COLLECTION_NAME = "analysis_claim"

        private fun AnalysisClaim.toDBObject() = BasicDBObject("_id", this.id)
                .append("q", this.q)
                .append("stage", this.stage.name)
                .append("error", this.error)
    }
}