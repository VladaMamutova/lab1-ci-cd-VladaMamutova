package ru.vladamamutova.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.vladamamutova.domain.Person
import ru.vladamamutova.repository.PersonRepository

@Controller
class PersonController (@Autowired val repository: PersonRepository) {

    @GetMapping("/")
    fun main(model: Model): String {
        model["title"] = "Person List"
        model["personList"] = repository.findAll()
        return "main"
    }

    @PostMapping
    public fun add(
            @RequestParam name: String, @RequestParam age: Int,
            @RequestParam address: String, @RequestParam work: String,
            model: Model
    ): String {
        repository.save(Person(name, age, address, work))
        model["title"] = "Person List"
        model["personList"] = repository.findAll()
        return "main"
    }
}