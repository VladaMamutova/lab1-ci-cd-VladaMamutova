package ru.vladamamutova.domain

import javax.persistence.*

@Entity
class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        // Т. к. схема базы данных создаётся реализацией JPA по описанию сущностей,
        // то стратегия генерации значений - автоматическая.
        var id: Int? = null

        var name: String? = null
        var age: Int? = null
        var address: String? = null
        var work: String? = null

        constructor()

        constructor(name: String?, age: Int?, address: String?, work: String?) {
                this.name = name
                this.age = age
                this.address = address
                this.work = work
        }
}
