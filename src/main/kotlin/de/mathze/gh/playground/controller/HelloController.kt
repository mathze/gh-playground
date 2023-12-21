package de.mathze.gh.playground.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

@RestController
class HelloController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("hi")
  fun sayHello(@RequestParam(name = "name", required = false) name: String?): String {
    log.info("Received request with $name")
    return "Hello " +
        if (name.isNullOrBlank()) {
          "who ever"
        } else {
          normalizeName(name)
        }.also {
          log.info("Responding: '$it'")
        }
  }

  private fun normalizeName(name: String) = name.lowercase()
    .trim()
    .split(Regex("\\s+"))
    .joinToString(" ") {
      it.replaceFirstChar { frstChar ->
        frstChar.titlecase(Locale.getDefault())
      }
    }
}
