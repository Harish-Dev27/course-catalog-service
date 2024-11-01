package com.kotlinspring.controller

import com.kotlinspring.model.CourseDTO
import com.kotlinspring.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(
    val courseService: CourseService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCourse(@RequestBody @Valid courseDTO: CourseDTO): CourseDTO{
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun getCourses(@RequestParam ("course", required = false) courseName: String?): List<CourseDTO> = courseService.getCourses(courseName)

    @PutMapping("/{id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO,
                     @PathVariable("id") courseId: Int): CourseDTO = courseService.updateCourse(courseDTO, courseId)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("id") courseId: Int) = courseService.deleteCourse(courseId)
}