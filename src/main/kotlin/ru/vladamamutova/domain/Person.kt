package ru.vladamamutova.domain

import javax.persistence.*

@Entity
class Person {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        // Т. к. схема базы данных создаётся реализацией JPA по описанию сущностей,
        // то стратегия генерации значений - автоматическая.
        private var id: Long? = null

        private var name: String? = null
        private var age: Int? = null
        private var address: String? = null
        private var work: String? = null

        constructor()

        constructor(name: String?, age: Int?, address: String?, work: String?) {
                this.name = name
                this.age = age
                this.address = address
                this.work = work
        }
}
