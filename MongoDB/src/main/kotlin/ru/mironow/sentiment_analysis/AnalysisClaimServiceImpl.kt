package ru.mironow.sentiment_analysis

import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.data_service.AnalysisClaimService

/**
 * MongoDB service for work with analysis claims
 *
 * Created By Alexander Mironow - 14.02.2020
 */
@Service
class AnalysisClaimServiceImpl(val database: DB) : AnalysisClaimService {
    override fun save(claim: AnalysisClaim) {
        val dbObject = claim.toDBObject()
        database.getCollection(COLLECTION_NAME).insert(dbObject)
    }

    override fun update(claim: AnalysisClaim) {
        val objectId = BasicDBObject("_id", claim.id)
        database.getCollection(COLLECTION_NAME).update(objectId, claim.toDBObject())
    }

    override fun getById(id: String): AnalysisClaim =database.getCollection(COLLECTION_NAME)
                .find(BasicDBObject("_id", id))
                .next()
                .toEntity()

    companion object {
        const val COLLECTION_NAME = "analysis_claim"

        private fun AnalysisClaim.toDBObject() = BasicDBObject("_id", this.id)
                .append("q", this.q)
                .append("stage", this.stage.name)
                .append("error", this.error)
                .append("percent", this.percent)

        private fun DBObject.toEntity() = AnalysisClaim(
                id = get("_id") as String,
                q = get("q") as String,
                stage = AnalysisClaimStage.valueOf(get("stage") as String),
                error = get("error")?.let { it as String },
                percent = get("percent") as Int
        )
    }
}