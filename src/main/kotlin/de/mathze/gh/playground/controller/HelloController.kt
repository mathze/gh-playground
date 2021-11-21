package de.mathze.gh.playground.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

@RestController
class HelloController {

  @GetMapping("hi")
  fun sayHello(@RequestParam(name = "name", required = false) name: String?): String {
    return "Hello " +
        if (name.isNullOrBlank()) {
          "who ever"
        } else {
          normalizeName(name)
        }
  }

  private fun normalizeName(name: String) = name.lowercase()
    .split(Regex("\\s+"))
    .joinToString(" ") {
      it.replaceFirstChar { frstChar ->
        frstChar.titlecase(Locale.getDefault())
      }
    }
}
