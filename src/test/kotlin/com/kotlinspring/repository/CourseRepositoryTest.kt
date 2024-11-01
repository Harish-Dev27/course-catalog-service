package com.kotlinspring.repository

import com.kotlinspring.utils.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream
import kotlin.test.assertEquals


@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll() //before each test delete all entries
        val courses = courseEntityList()
        courseRepository.saveAll(courses) //save all courses
    }

    @Test
    fun testCustomJPAMethod() {
        val courses = courseRepository.findByNameContaining("SpringBoot")
        println("Courses ::$courses")


        assertEquals(2, courses.size)
    }

    @Test
    fun findCourseByName() {
        val courses = courseRepository.findCourseByName("SpringBoot")
        println("Courses ::$courses")


        assertEquals(2, courses.size)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCourseByName_parameterized(name: String, expectedSize: Int) {
        val courses = courseRepository.findCourseByName(name)
        println("Courses ::$courses")

        assertEquals(expectedSize, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(
                Arguments.arguments("SpringBoot", 2), Arguments.arguments("Wiremock", 1)
            )
        }
    }
}