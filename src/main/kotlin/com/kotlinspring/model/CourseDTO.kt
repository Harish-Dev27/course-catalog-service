package com.kotlinspring.model

import com.kotlinspring.persistence.CourseEntity
import com.kotlinspring.persistence.InstructorEntity
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "courseDTO.name cannot be blank")
    val name: String,
    @get:NotBlank(message = "courseDTO.category cannot be blank")
    val category: String,
    @get:NotNull(message = "courseDTO.instructorId cannot be null")
    val instructorId: Int? = null ,
)


fun CourseDTO.toCourseEntity(instructorEntity: InstructorEntity?) =
    CourseEntity(
        null,
        this.name,
        this.category,
        instructorEntity,
    )