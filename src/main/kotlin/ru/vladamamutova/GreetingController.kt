package ru.vladamamutova

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GreetingController {
    @GetMapping("/")
    fun main(model: Model): String {
        model["name"] = "World"
        return "greeting"
    }

    @GetMapping("/greeting")
    fun greeting(
    @RequestParam(name="name", required=false, defaultValue="user") name: String,
    model: Model
    ): String {
        model["name"] = name
        return "greeting"
    }
}
