package com.kotlinspring.model

import com.kotlinspring.persistence.CourseEntity

data class CourseDTO(
    val id: Int?,
    val name: String,
    val category: String,
)


fun CourseDTO.toCourseEntity() =
    CourseEntity(
        null,
        this.name,
        this.category,
    )