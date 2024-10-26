package com.kotlinspring.repository

import com.kotlinspring.persistence.CourseEntity
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<CourseEntity, Int> {
}