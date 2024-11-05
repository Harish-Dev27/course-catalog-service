package com.kotlinspring.service

import com.kotlinspring.controller.InstructorController
import com.kotlinspring.model.InstructorDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals


@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorService: InstructorService

    @Test
    fun addInstructorTest() {
        //given
        val instructor = InstructorDTO(null, "Harish")

        //when
        every { instructorService.addInstructor(any()) } returns InstructorDTO(1, "Harish")
        val response = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructor)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()

        //then
        Assertions.assertTrue(response.responseBody!!.id != null)
    }

    @Test
    fun addInstructor_validation_test() {
        //given
        val instructor = InstructorDTO(null, "")

        //when
        every { instructorService.addInstructor(any()) } returns InstructorDTO(1, "Harish")
        val response = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructor)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()

        //then
        assertEquals("instructorDto.name cannot be blank", response.responseBody)
    }
}