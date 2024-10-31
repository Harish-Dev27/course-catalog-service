package com.kotlinspring.repository

import com.kotlinspring.utils.courseEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
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
    fun testCustomJPAMethod(){
        val courses = courseRepository.findByNameContaining("SpringBoot")
        println("Courses ::$courses")


        assertEquals(2, courses.size)
    }

    @Test
    fun findCourseByName(){
        val courses = courseRepository.findCourseByName("SpringBoot")
        println("Courses ::$courses")


        assertEquals(2, courses.size)
    }
}