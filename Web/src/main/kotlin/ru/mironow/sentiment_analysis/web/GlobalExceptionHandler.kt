package ru.mironow.sentiment_analysis.web

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.mironow.sentiment_analysis.web.response.Response

/**
 * REST Error Handler
 *
 * Created By Alexander Mironow - 14.02.2020
 */
@RestControllerAdvice
open class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun runtimeException(exception: RuntimeException): Response = Response(
            errorCode = 1,
            errorMessage = exception.message ?: DEFAULT_ERROR,
            `object` = null
    )

    companion object {
        private const val DEFAULT_ERROR = "Неизвестная ошибка"
    }
}