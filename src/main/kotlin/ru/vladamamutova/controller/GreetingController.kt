package ru.vladamamutova.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {
    @GetMapping("/")
    fun greeting(
    @RequestParam(name="name", required=false, defaultValue="user") name: String,
    model: Model
    ): String {
        model["name"] = name
        return "greeting"
    }
}
