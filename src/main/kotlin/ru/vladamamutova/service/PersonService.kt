package ru.vladamamutova.service

import ru.vladamamutova.domain.Person

interface PersonService {
    fun create(person: Person): Int
    fun getAll(): List<Person>
    fun getById(id: Int): Person
    fun update(id: Int, person: Person)
    fun delete(id: Int)
}
