package ru.vladamamutova.domain

class ErrorValidationResponse(override val message: String,
                              val errors: Map<String, String>
) : ErrorResponse(message)
