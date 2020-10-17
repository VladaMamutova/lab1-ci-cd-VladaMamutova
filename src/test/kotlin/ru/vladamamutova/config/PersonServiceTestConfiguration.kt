package ru.vladamamutova.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import ru.vladamamutova.repository.PersonRepository
import ru.vladamamutova.service.PersonService
import ru.vladamamutova.service.PersonServiceImpl

@TestConfiguration
open class PersonServiceTestConfiguration {
    @MockBean
    private lateinit var personRepository: PersonRepository

    @Bean
    open fun personService(): PersonService {
        return PersonServiceImpl(personRepository)
    }
}
