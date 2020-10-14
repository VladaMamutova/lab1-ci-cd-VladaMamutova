package ru.vladamamutova.service

import org.springframework.stereotype.Service
import ru.vladamamutova.domain.Person
import ru.vladamamutova.domain.PersonRequest
import ru.vladamamutova.domain.PersonResponse
import ru.vladamamutova.domain.toPersonResponse
import ru.vladamamutova.exception.PersonNotFoundException
import ru.vladamamutova.repository.PersonRepository

@Service
class PersonServiceImpl(private val repository: PersonRepository)
    : PersonService {

    override fun create(request: PersonRequest): Int {
        val person = Person(request.name, request.age,
                request.address, request.work)
        return repository.save(person).id!!
    }

    override fun getAll(): List<PersonResponse> {
        return repository.findAll().map { it.toPersonResponse() }
    }

    override fun getById(id: Int): PersonResponse {
        return repository.findById(id)
                .orElseThrow { throw PersonNotFoundException(id) }
                .toPersonResponse()
    }

    override fun update(id: Int, request: PersonRequest): PersonResponse {
        val person = repository.findById(id)
                .orElseThrow { throw PersonNotFoundException(id) }

        person.id = id
        person.name = request.name
        person.age = request.age ?: person.age
        person.address = request.address ?: person.address
        person.work = request.work ?: person.work
        repository.save(person)

        return person.toPersonResponse()
    }

    override fun delete(id: Int) {
        if (!repository.existsById(id)) {
            throw PersonNotFoundException(id)
        }
        repository.deleteById(id)
    }
}
