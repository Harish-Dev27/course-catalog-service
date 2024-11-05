package com.kotlinspring.persistence

import com.kotlinspring.model.InstructorDTO
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "Instructor")
data class InstructorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    val name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    var courses: MutableList<CourseEntity> = mutableListOf()
)


fun InstructorEntity.toDto(): InstructorDTO {
    return InstructorDTO(this.id, this.name)
}