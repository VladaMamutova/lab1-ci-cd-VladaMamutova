package ru.vladamamutova.domain

import javax.persistence.*
import javax.validation.constraints.*

@Entity
// Если не используется атрибут name аннотации @Table,
// то по умолчанию таблица будет названа именем сущности.
@Table(name = "persons")
class Person {
    @Id
    // Т. к. схема базы данных создаётся реализацией JPA по описанию сущностей,
    // то стратегия генерации значений - автоматическая.
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    // only to specify table column properties, it doesn't provide validations
    @Column(nullable = false, length = 80)
    // string must not be null and the trimmed length is greater than zero
    @NotBlank
    // @Size makes the bean independent of JPA and its vendors such as Hibernate.
    // As a result, this is more portable than @Length.
    @Size(min = 1, max = 80)
    var name: String? = null

    @Positive
    @Max(value = 150)
    var age: Int? = null

    var address: String? = null
    var work: String? = null

    constructor() // обязателен для JPA

    constructor(name: String?, age: Int?, address: String?, work: String?) {
        this.name = name
        this.age = age
        this.address = address
        this.work = work
    }

    constructor(id: Int?, name: String?, age: Int?, address: String?,
                work: String?
    ) : this(name, age, address, work) {
        this.id = id
    }
}
