package com.kotlinspring.controller


import com.kotlinspring.model.InstructorDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient


    @Test
    fun addInstructorTest() {
        //given
        val instructor = InstructorDTO(null, "Harish")

        //when
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
}