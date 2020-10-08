package ru.vladamamutova.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.vladamamutova.domain.Person
import ru.vladamamutova.service.PersonService


// @RestController includes the @Controller and @ResponseBody annotations
// and as a result, simplifies the controller implementation.
@RestController
class PersonController(@Autowired private val personService: PersonService) {
    @PostMapping("/persons", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPerson(@RequestBody person: Person): ResponseEntity<Void> {
        val id = personService.create(person)
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri()).build()
    }

    // The "produces" attribute is optional. If it is not used,
    // the produces clause is determined automatically.
    @GetMapping("/persons", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllPersons(): ResponseEntity<List<Person>> {
        val personList = personService.getAll()
        return if (personList.isNotEmpty())
            ResponseEntity(personList, HttpStatus.OK)
        else ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/persons/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPersonById(@PathVariable id: Int): ResponseEntity<Person> {
        val person = personService.getById(id)
        return ResponseEntity(person, HttpStatus.OK)
    }

    @PatchMapping("/persons/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePerson(@PathVariable id: Int, @RequestBody person: Person)
            : ResponseEntity<Void> {
        personService.update(id, person)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/persons/{id}")
    fun deletePerson(@PathVariable id: Int): ResponseEntity<Void> {
        personService.delete(id)
        return ResponseEntity.ok().build()
    }
}
