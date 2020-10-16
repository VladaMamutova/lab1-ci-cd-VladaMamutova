package ru.vladamamutova.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.vladamamutova.domain.ErrorResponse
import ru.vladamamutova.domain.ErrorValidationResponse
import ru.vladamamutova.exception.PersonNotFoundException
import javax.validation.ConstraintViolationException


// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@RestControllerAdvice
class ErrorController {
    private val logger = LoggerFactory.getLogger(ErrorController::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotFoundException::class)
    fun handleNotFoundException(exception: PersonNotFoundException)
            : ErrorResponse {
        logger.error("Person Not Found Exception: " + exception.message)
        return ErrorResponse(exception.message ?: exception.stackTraceToString())
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(exception: MethodArgumentNotValidException)
            : ErrorValidationResponse {
        val errors: Map<String, String> =
                exception.bindingResult.fieldErrors.associateBy(
                        { it.field },
                        { "${it.rejectedValue} is wrong value: ${it.defaultMessage}" })
        logger.error("Validation Exception: $errors")
        return ErrorValidationResponse("Not Valid Arguments", errors)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransactionSystemException::class)
    fun handleConstrainException(exception: TransactionSystemException)
            : ErrorResponse {
        return if (exception.rootCause is ConstraintViolationException) {
            val violations = (exception.rootCause as
                    ConstraintViolationException).constraintViolations
            val errors = violations.associateBy(
                    { "${it.rootBeanClass.simpleName}.${it.propertyPath}" },
                    { "${it.invalidValue} is wrong value: ${it.message}" }
            )
            logger.error("Validation Exception: $errors")
            ErrorValidationResponse("Not Valid Arguments", errors)
        } else {
            logger.error("Transaction System Exception", exception)
            ErrorResponse(exception.message ?: exception.stackTraceToString())
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception): ErrorResponse {
        logger.error("Unexpected exception", exception)
        return ErrorResponse(exception.message ?: exception.stackTraceToString())
    }
}