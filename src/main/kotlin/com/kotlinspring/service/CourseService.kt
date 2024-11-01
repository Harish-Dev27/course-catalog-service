package com.kotlinspring.service

import com.kotlinspring.model.CourseDTO
import com.kotlinspring.model.toCourseEntity
import com.kotlinspring.persistence.toDto
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service
import com.kotlinspring.exception.CourseNotFoundException


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

    fun getCourses(courseName: String?): List<CourseDTO> {
        return courseName?.let {
            courseRepository.findCourseByName(courseName).map { it.toDto() }
        } ?: courseRepository.findAll().map { it.toDto() }
    }

    fun updateCourse(courseDTO: CourseDTO, courseId: Int): CourseDTO {

        val course = courseRepository.findById(courseId)
        return if(course.isPresent){
            course.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    it.toDto()
                }
        }else{
            throw CourseNotFoundException("No course found for the given Id:: $courseId")
        }
    }

    fun deleteCourse(courseId: Int){
        val course = courseRepository.findById(courseId)
        if(course.isPresent){
            courseRepository.deleteById(courseId)
        }else{
            throw CourseNotFoundException("No course found for the given Id:: $courseId")
        }
    }
}
