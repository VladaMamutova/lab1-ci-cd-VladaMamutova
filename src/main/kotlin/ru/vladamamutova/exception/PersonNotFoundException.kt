package ru.vladamamutova.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class PersonNotFoundException(id: Int):
        RuntimeException("Person $id not found.")
