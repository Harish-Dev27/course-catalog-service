package com.kotlinspring.controller

import com.kotlinspring.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(
    val greetingService: GreetingService,
) {

    @GetMapping("/{name}")
    fun returnGreeting(@PathVariable("name") username: String): String {
//        return "Hello, $username"
        return greetingService.retrieveGreeting(username)
    }
}