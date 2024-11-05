package com.kotlinspring.repository

import com.kotlinspring.persistence.InstructorEntity
import org.springframework.data.repository.CrudRepository

interface InstructorRepository: CrudRepository<InstructorEntity, Int> {

}
