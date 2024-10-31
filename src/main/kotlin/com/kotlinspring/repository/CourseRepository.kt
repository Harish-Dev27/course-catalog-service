package com.kotlinspring.repository

import com.kotlinspring.persistence.CourseEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<CourseEntity, Int> {


    fun findByNameContaining(courseName: String): List<CourseEntity>


    @Query(value = "SELECT * FROM COURSES WHERE name LIKE %?1%", nativeQuery = true)
    fun findCourseByName(courseName: String): List<CourseEntity>
}