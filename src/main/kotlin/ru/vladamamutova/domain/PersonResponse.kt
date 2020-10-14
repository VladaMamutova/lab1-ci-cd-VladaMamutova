package ru.vladamamutova.domain

fun Person.toPersonResponse(): PersonResponse {
    return PersonResponse(this.id!!, this.name!!, this.age, this.address,
            this.work)
}

class PersonResponse(var id: Int,
                     var name: String,
                     var age: Int?,
                     var address: String?,
                     var work: String?)
