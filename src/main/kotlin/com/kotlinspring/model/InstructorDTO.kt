package com.kotlinspring.model

import com.kotlinspring.persistence.InstructorEntity
import jakarta.validation.constraints.NotBlank


data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "instructorDto.name cannot be blank")
    val name: String,
)


fun InstructorDTO.toEntity() = InstructorEntity(null, this.name)