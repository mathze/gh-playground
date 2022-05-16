package de.mathze.gh.playground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringApp

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
  runApplication<SpringApp>(*args)
}
