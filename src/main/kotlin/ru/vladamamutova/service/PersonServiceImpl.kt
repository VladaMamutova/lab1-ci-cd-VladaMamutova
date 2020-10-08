package ru.vladamamutova.service

import org.springframework.stereotype.Service
import ru.vladamamutova.domain.Person
import ru.vladamamutova.exception.PersonNotFoundException
import ru.vladamamutova.repository.PersonRepository

@Service
class PersonServiceImpl(private val repository: PersonRepository)
    : PersonService {

    override fun create(person: Person): Int {
        val newPerson = repository.save(person)
        return newPerson.id!!
    }

    override fun getAll(): List<Person> {
        return repository.findAll().toList()
    }

    override fun getById(id: Int): Person {
        return repository.findById(id).orElseThrow {
            throw PersonNotFoundException(id)
        }
    }

    override fun update(id: Int, person: Person) {
        val existingPerson = repository.findById(id)
                .orElseThrow { throw PersonNotFoundException(id) }

        existingPerson.id = id
        existingPerson.name = person.name ?: existingPerson.name
        existingPerson.age = person.age ?: existingPerson.age
        existingPerson.address = person.address ?: existingPerson.address
        existingPerson.work = person.work ?: existingPerson.work

        repository.save(person)
    }

    override fun delete(id: Int) {
        if (!repository.existsById(id)) {
            throw PersonNotFoundException(id)
        }
        repository.deleteById(id)
    }
}
