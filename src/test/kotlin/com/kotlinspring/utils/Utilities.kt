package com.kotlinspring.utils

import com.kotlinspring.model.CourseDTO
import com.kotlinspring.persistence.CourseEntity

fun courseEntityList() = listOf(
    CourseEntity(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"),
    CourseEntity(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    CourseEntity(null,
        "Wiremock for Java Developers", "Development" ,
    )
)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Dilip Sundarraj",
//    instructorId: Int? = 1
) = CourseDTO(
    id,
    name,
    category,
//    instructorId
)