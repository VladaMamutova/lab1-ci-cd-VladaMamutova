package ru.vladamamutova.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.vladamamutova.domain.PersonRequest
import ru.vladamamutova.domain.PersonResponse
import ru.vladamamutova.service.PersonService


// @RestController includes the @Controller and @ResponseBody annotations
// and as a result, simplifies the controller implementation.
@RestController
class PersonController(@Autowired private val personService: PersonService) {
    @PostMapping("/persons", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPerson(
            @RequestBody request: PersonRequest): ResponseEntity<Void> {
        val id = personService.create(request)
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri()).build()
    }

    // The "produces" attribute is optional. If it is not used,
    // the produces clause is determined automatically.
    @GetMapping("/persons", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllPersons() = personService.getAll()

    @GetMapping("/persons/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPersonById(@PathVariable id: Int) = personService.getById(id)

    @PatchMapping("/persons/{id}",
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePerson(@PathVariable id: Int, @RequestBody request: PersonRequest)
            : PersonResponse {
        return personService.update(id, request)
    }

    @DeleteMapping("/persons/{id}")
    fun deletePerson(@PathVariable id: Int) = personService.delete(id)
}
