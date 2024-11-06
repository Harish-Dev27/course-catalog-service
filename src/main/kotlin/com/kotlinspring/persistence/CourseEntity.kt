package com.kotlinspring.persistence

import com.kotlinspring.model.CourseDTO
import jakarta.persistence.*


@Entity
@Table(name = "Courses")
data class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    val instructor: InstructorEntity? = null
){
    override fun toString(): String {
        return "CourseEntity(id=$id, name='$name', category='$category', instructor=${instructor!!.id})"
    }
}


fun CourseEntity.toDto() = CourseDTO(this.id, this.name, this.category, this.instructor!!.id)