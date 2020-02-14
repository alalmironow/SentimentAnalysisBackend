package ru.mironow.sentiment_analysis

import org.springframework.stereotype.Component
import ru.mironow.sentiment_analysis.data_service.AnalysisClaimService
import java.lang.RuntimeException
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

/**
 * Component for create analysis claim
 *
 * Created By Alexander Mironow - 13.02.2020
 */
@Component
class AnalysisClaimUseCase(val analysisClaimService: AnalysisClaimService) {
    /**
     * Создать заявку на анализ мнений по запросу [q]
     */
    fun createAnalysisClaim(q: String): AnalysisClaim {
        if (q.isBlank() || q.trim().length < MIN_LENGTH_QUERY) {
            throw RuntimeException("Минимальная длина поискового запроса $MIN_LENGTH_QUERY")
        }
        var claim = AnalysisClaim(
                id = generateUniqueKey(),
                q = q.trim()
        )
        analysisClaimService.save(claim)
        return claim
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