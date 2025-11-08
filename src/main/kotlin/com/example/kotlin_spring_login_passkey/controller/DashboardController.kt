package com.example.kotlin_spring_login_passkey.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DashboardController {

    @GetMapping
    fun index(): String{
        return "Hello World!"
    }
}