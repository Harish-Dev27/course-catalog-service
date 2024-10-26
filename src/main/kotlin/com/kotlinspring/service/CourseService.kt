package com.kotlinspring.service

import com.kotlinspring.model.CourseDTO
import com.kotlinspring.model.toCourseEntity
import com.kotlinspring.persistence.toDto
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service


@Service
class CourseService(
    val courseRepository: CourseRepository,
) {
    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val course = courseDTO.toCourseEntity()
        courseRepository.save(course)

        logger.info("Course saved in the Db:: $course")
        return course.toDto()
    }
}
