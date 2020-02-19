package ru.mironow.sentiment_analysis.business.analysys_claim

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.mironow.sentiment_analysis.*
import ru.mironow.sentiment_analysis.data_service.AnalysisClaimService
import ru.mironow.sentiment_analysis.data_service.SentimentTextService
import ru.mironow.sentiment_analysis.data_service.SocialNetworkService
import ru.mironow.sentiment_analysis.sentiment_analysis_service.SentimentAnalysisService
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

/**
 * Component for create analysis claim
 *
 * Created By Alexander Mironow - 13.02.2020
 */
@Component
open class AnalysisClaimUseCaseImpl(
        val analysisClaimService: AnalysisClaimService,
        val socialNetworkService: SocialNetworkService,
        val sentimentAnalysisService: SentimentAnalysisService,
        val sentimentTextService: SentimentTextService
) : AnalysisClaimUseCase {
    /**
     * Создать заявку на анализ мнений по запросу [q]
     */
    override fun createAnalysisClaim(q: String): AnalysisClaim {
        if (q.isBlank() || q.trim().length < MIN_LENGTH_QUERY) {
            throw RuntimeException("Минимальная длина поискового запроса $MIN_LENGTH_QUERY")
        }
        var claim = AnalysisClaim(
                id = generateUniqueKey(),
                q = q.trim()
        )
        analysisClaimService.save(claim)

        executeAnalysis(claim)

        return claim
    }

    @Async
    override fun executeAnalysis(claim: AnalysisClaim) {
        var processClaim = claim.copy(stage = AnalysisClaimStage.LOAD_DATA)
        //analysisClaimService.save(processClaim)

        val data = socialNetworkService.getData(claim.q)

        processClaim = processClaim.copy(stage = AnalysisClaimStage.EXECUTE)
        //analysisClaimService.save(processClaim)

        val count = data.size
        var counter = 0

        val listStatistic = mutableListOf<StatisticDay>()

        data.forEach {
            listStatistic.add( getDayStatistic(it.data) {
                counter += it.data.size
                val percent = counter/count
                if (percent > processClaim.percent + 1) {
                    processClaim = processClaim.copy(percent = counter / count)
                    //analysisClaimService.save(processClaim)
                }
            })
        }

        println("test")
    }

    private fun getDayStatistic(data: List<SocialData>, observer: () -> Unit): StatisticDay {
        val count = data.size
        var countPosts = 0
        var countComments = 0
        var countPositive = 0
        var countNegative = 0

        data.forEach {
            when (it.type) {
                SocialDataType.POST -> countPosts += 1
                SocialDataType.COMMENT -> countComments += 1
            }
            val sentiment = sentimentAnalysisService.analysisSentiment(it.data)
            val sentimentText = SentimentText(text = it.data, sentiment = sentiment)
            sentimentTextService.save(sentimentText)

            if (sentiment) {
                countPositive += 1
            } else {
                countNegative += 1
            }

            observer.invoke()
        }

        return StatisticDay(
                countPositive = countPositive,
                countNegative = countNegative,
                count = count,
                statisticMessage = "${count} записи. Из них ${countPosts} постов и ${countComments} комментариев"
        )
    }

    private fun generateUniqueKey(): String {
        val uuid = UUID.randomUUID().toString()
        with (MessageDigest.getInstance("MD5")) {
            update(uuid.toByteArray())
            return DatatypeConverter.printHexBinary(digest()).toUpperCase()
        }
    }

    companion object {
        private const val MIN_LENGTH_QUERY = 3
    }
}