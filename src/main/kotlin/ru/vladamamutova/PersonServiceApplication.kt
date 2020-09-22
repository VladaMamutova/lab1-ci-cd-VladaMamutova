package ru.vladamamutova

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

// The @SpringBootApplication marks the class with the @Configuration,
// @EnableAutoConfiguration and @ComponentScan annotations.
// The @Configuration annotation forces the use of the open keyword.
@SpringBootApplication
open class PersonServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(PersonServiceApplication::class.java, *args)
}
