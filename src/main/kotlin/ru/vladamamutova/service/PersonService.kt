package ru.vladamamutova.service

import ru.vladamamutova.domain.PersonRequest
import ru.vladamamutova.domain.PersonResponse

interface PersonService {
    fun create(request: PersonRequest): Int
    fun getAll(): List<PersonResponse>
    fun getById(id: Int): PersonResponse
    fun update(id: Int, request: PersonRequest): PersonResponse
    fun delete(id: Int)
}
