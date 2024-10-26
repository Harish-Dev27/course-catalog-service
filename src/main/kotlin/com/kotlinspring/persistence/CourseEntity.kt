package com.kotlinspring.persistence

import com.kotlinspring.model.CourseDTO
import jakarta.persistence.*


@Entity
@Table(name = "Courses")
data class CourseEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    val name: String,

    val category: String,
)


fun CourseEntity.toDto() = CourseDTO(this.id, this.name, this.category)