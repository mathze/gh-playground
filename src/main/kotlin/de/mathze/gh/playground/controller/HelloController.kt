package de.mathze.gh.playground.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

  @GetMapping("hi")
  fun sayHello(@RequestParam(name = "name", required = false) name: String?): String {
    return "Hello " +
        if (name.isNullOrBlank()) {
          "who ever"
        } else {
          name.toLowerCase().split(Regex("\\s+")).joinToString(" ") { it.capitalize() }
        }
  }
}