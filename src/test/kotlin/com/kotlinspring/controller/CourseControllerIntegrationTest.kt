package com.kotlinspring.controller

import com.kotlinspring.model.CourseDTO
import com.kotlinspring.persistence.CourseEntity
import com.kotlinspring.repository.CourseRepository
import com.kotlinspring.utils.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder
import kotlin.test.assertEquals


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll() //before each test delete all entries
        val courses = courseEntityList()
        courseRepository.saveAll(courses) //save all courses
    }

    @Test
    fun createCourseTest() {
        //given
        val courseDto = CourseDTO(null, "Build with Kotlin", "DEVELOPMENT")

        //when
        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        //then
        Assertions.assertTrue {
            savedCourseDto!!.id != null
        }
    }

    @Test
    fun getAllCoursesTest(){

         val response = webTestClient
             .get()
             .uri("/v1/courses")
             .exchange()
             .expectStatus().isOk
             .expectBodyList(CourseDTO::class.java)
             .returnResult()
             .responseBody

        println(response)
        assertEquals(3,response!!.size)
    }

    @Test
    fun getAllSpecificCoursesTest(){
        val uri = UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course","SpringBoot").toUriString()

        val response = webTestClient
            .get()
            .uri(uri)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println(response)
        assertEquals(2,response!!.size)
    }

    @Test
    fun updateCourseTest() {
        //given
        val entity = CourseEntity(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")
        courseRepository.save(entity)

        val requestBody = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot 2", "Development")


        //when
        val response = webTestClient.put()
            .uri("/v1/courses/{id}",entity.id)
            .bodyValue(requestBody)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody


        //then
        assertEquals("Build Restful APIs using Kotlin and SpringBoot 2", response!!.name)

    }

    @Test
    fun deleteCourseTest() {
        //given
        val entity = CourseEntity(null, "Build Restful APIs using Kotlin and SpringBoot", "Development")
        courseRepository.save(entity)

        //when
        val response = webTestClient.delete()
            .uri("/v1/courses/{id}",entity.id)
            .exchange()
            .expectStatus().isNoContent

    }
}