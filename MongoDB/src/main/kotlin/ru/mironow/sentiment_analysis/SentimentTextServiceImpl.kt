package ru.mironow.sentiment_analysis

import com.mongodb.BasicDBObject
import com.mongodb.DB
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.data_service.SentimentTextService

@Service
class SentimentTextServiceImpl(val database: DB): SentimentTextService {
    override fun save(sentimentText: SentimentText) {
        val dbObject = sentimentText.toDBObject()
        database.getCollection(COLLECTION_NAME)
                .save(dbObject)
    }

    companion object {
        const val COLLECTION_NAME = "analysis_text"

        private fun SentimentText.toDBObject() = BasicDBObject()
                .append("text", this.text)
                .append("sentiment", this.sentiment)
    }
}