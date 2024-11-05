package com.kotlinspring.service

import com.kotlinspring.model.InstructorDTO
import com.kotlinspring.model.toEntity
import com.kotlinspring.persistence.toDto
import com.kotlinspring.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService(
    val instructorRepository: InstructorRepository,
) {
    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        val instructor = instructorDTO.toEntity()

        instructorRepository.save(instructor)
        return instructor.toDto()
    }

}
