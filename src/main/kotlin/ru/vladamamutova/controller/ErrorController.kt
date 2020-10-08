package ru.vladamamutova.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vladamamutova.domain.ErrorResponse
import ru.vladamamutova.exception.PersonNotFoundException


// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@RestControllerAdvice
class ErrorController {
    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFoundException::class)
    fun handleNotFoundException(exception: PersonNotFoundException): ErrorResponse {
        logger.error("Person Not Found Exception", exception)
        return ErrorResponse(exception.message ?: exception.stackTraceToString())
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception): ErrorResponse {
        logger.error("Unexpected exception", exception)
        return ErrorResponse(exception.message ?: exception.stackTraceToString())
    }
}