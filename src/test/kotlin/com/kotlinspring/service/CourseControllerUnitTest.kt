package com.kotlinspring.service

import com.kotlinspring.controller.CourseController
import com.kotlinspring.model.CourseDTO
import com.kotlinspring.persistence.CourseEntity
import com.kotlinspring.utils.courseDTO
import com.kotlinspring.utils.courseEntityList
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun createCourseTest() {
        //given
        val courseDto = CourseDTO(null, "Build with Kotlin", "DEVELOPMENT")

        //when
        every { courseService.addCourse(any()) } returns courseDTO(id=1)

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
    fun createCourseTest_validation() {
        //given
        val courseDto = CourseDTO(null, "", "")

        //when
        every { courseService.addCourse(any()) } returns courseDTO(id=1)

        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDTO.category cannot be blank, courseDTO.name cannot be blank",savedCourseDto)
    }

    @Test
    fun test_global_exception_handler() {
        //given
        val courseDto = CourseDTO(null, "Build with Kotlin", "DEVELOPMENT")
        val errorMessage = "Unexpected error occurred"

        //when
        every { courseService.addCourse(any()) } throws RuntimeException(errorMessage)
        val savedCourseDto = webTestClient.post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        //then
        assertEquals(errorMessage,savedCourseDto)
    }

    @Test
    fun getAllCoursesTest(){
        every { courseService.getCourses(any()) }.returnsMany(
                listOf(courseDTO(id=1),
                    courseDTO(id=2, "Sample course")
                )
        )
        val response = webTestClient
            .get()
            .uri("/v1/courses")
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
        val requestBody = CourseDTO(null, "Build Restful APIs using Kotlin and SpringBoot 2", "Development")
        every { courseService.updateCourse(any(), any()) } returns courseDTO(100, "Build Restful APIs using Kotlin and SpringBoot 2")


        //when
        val response = webTestClient.put()
            .uri("/v1/courses/{id}",100)
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
        //when
        every { courseService.deleteCourse(any()) } just runs
        val response = webTestClient.delete()
            .uri("/v1/courses/{id}",100)
            .exchange()
            .expectStatus().isNoContent

    }

}