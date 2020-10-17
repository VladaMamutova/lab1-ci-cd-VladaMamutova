package ru.vladamamutova.controller

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.vladamamutova.config.PersonServiceTestConfiguration
import ru.vladamamutova.domain.Person
import ru.vladamamutova.exception.PersonNotFoundException
import ru.vladamamutova.repository.PersonRepository
import ru.vladamamutova.utils.TestUtils
import java.util.Optional.of


// @RunWith for @Autowired and @MockBean
@RunWith(SpringRunner::class) // alias for SpringJUnit4ClassRunner
// @WebMvcTest will only scan beans related to Web layer,
// like @Controller, @ControllerAdvice, WebMvcConfigurer etc.
@WebMvcTest(PersonController::class)
@Import(PersonServiceTestConfiguration::class)
class PersonControllerTest {
    companion object {
        private const val PERSON_ID = 1
        private const val URL = "/persons/"
    }

    @Autowired
    // Provide Spring MVC infrastructure without starting the HTTP Server.
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var personRepository: PersonRepository

    @Test
    fun testGetPersonSuccess() {
        // prepare data and mock's behaviour
        val person = createValidPerson(PERSON_ID)
        // stub what a service layer should return
        `when`(personRepository.findById(any(Int::class.java)))
            .thenReturn(of(person))

        // execute
        val result = mockMvc
            .perform(
                    MockMvcRequestBuilders
                        .get(URL + PERSON_ID)
                        .accept(MediaType.APPLICATION_JSON)
            )
            .andReturn()

        // verify
        assertEquals(
                "Incorrect response status",
                HttpStatus.OK.value(),
                result.response.status
        )
        // verify that service method was called once
        verify(personRepository).findById(any(Int::class.java))
        val resultPerson = TestUtils.jsonToObject(
                result.response.contentAsString,
                Person::class.java
        )
        assertNotNull(resultPerson)
        assertEquals(PERSON_ID, resultPerson.id)
    }

    @Test
    fun testGetPersonNotExist() {
        // prepare data and mock's behaviour
        // not required as person Not Exist scenario

        // execute
        val result = mockMvc.perform(
                MockMvcRequestBuilders
                    .get(URL + PERSON_ID)
                    .accept(MediaType.APPLICATION_JSON)
        ).andReturn()

        // verify
        assertEquals(
                "Incorrect response status",
                HttpStatus.NOT_FOUND.value(),
                result.response.status
        )
        verify(personRepository).findById(any(Int::class.java))
        assertEquals(
                result.resolvedException.javaClass,
                PersonNotFoundException::class.java
        )
    }

    @Test
    fun testAddPersonSuccess() {
        // prepare data and mock's behaviour
        val person = createValidPerson(PERSON_ID)
        `when`(personRepository.save(any(Person::class.java))).thenReturn(person)

        // execute
        val result = mockMvc.perform(
                MockMvcRequestBuilders
                    .post(URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(TestUtils.objectToJson(person)!!)
        ).andReturn()

        // verify
        assertEquals(
                "Incorrect response status",
                HttpStatus.CREATED.value(),
                result.response.status
        )
        verify(personRepository).save(any(Person::class.java))
        assertNotNull(result.response.getHeader("location"))
        assertTrue(
                result.response
                    .getHeader("location")!!
                    .endsWith(PERSON_ID.toString())
        )
    }

    @Test
    fun testUpdatePersonSuccess() {
        val person = createValidPerson(-1)
        `when`(personRepository.save(any(Person::class.java))).thenReturn(person)
        `when`(personRepository.findById(any(Int::class.java)))
            .thenReturn(of(person))

        val personResult = createValidPerson(PERSON_ID)
        mockMvc
            .perform(
                    MockMvcRequestBuilders
                        .patch(URL + PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(person)!!)

            )
            .andExpect(status().isOk)
            .andExpect(content().json(TestUtils.objectToJson(personResult)!!))

        verify(personRepository).findById(any(Int::class.java))
        verify(personRepository).save(any(Person::class.java))
    }

    @Test
    fun testDeletePersonSuccess() {
        `when`(personRepository.existsById(PERSON_ID)).thenReturn(true)

        mockMvc
            .perform(MockMvcRequestBuilders.delete(URL + PERSON_ID))
            .andExpect(status().isOk)

        verify(personRepository).existsById(any(Int::class.java))
        verify(personRepository).deleteById(any(Int::class.java))
    }

    private fun createValidPerson(id: Int): Person {
        return Person(id, "Alice", 20, "Moscow", "Yandex")
    }
}
