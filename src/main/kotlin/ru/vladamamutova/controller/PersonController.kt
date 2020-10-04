package ru.vladamamutova.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.vladamamutova.domain.Person
import ru.vladamamutova.service.PersonService


// @RestController includes the @Controller and @ResponseBody annotations
// and as a result, simplifies the controller implementation.
@RestController
class PersonController(@Autowired private val personService: PersonService) {
    @PostMapping("/persons")
    fun createPerson(@RequestBody person: Person): ResponseEntity<Void> {
        val id = personService.create(person)
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri()).build()
    }

    @GetMapping("/persons")
    fun getAllPersons(): ResponseEntity<List<Person>> {
        val personList = personService.getAll()
        return if (personList.isNotEmpty())
            ResponseEntity<List<Person>>(personList, HttpStatus.OK)
        else ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/persons/{id}")
    fun getPersonById(@PathVariable id: Int): ResponseEntity<Person> {
        val person = personService.getById(id)
        return ResponseEntity<Person>(person, HttpStatus.OK)
    }

    @PatchMapping("/persons/{id}")
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
