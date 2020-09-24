package ru.vladamamutova.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.vladamamutova.domain.Person

@Repository
interface PersonRepository : CrudRepository<Person, Long> {
}
