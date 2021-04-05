package de.mathze.gh.playground.controller

import de.mathze.gh.playground.SpringApp
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(
  classes = [SpringApp::class]
)
@AutoConfigureMockMvc
internal class HelloControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  fun sayHelloNoName() {
    mvc.perform(
      get("/hi")
    ).andExpect(
      status().isOk
    ).andExpect(
      content().string("Hello who ever")
    )
  }

  @Test
  fun sayHelloToMe() {
    mvc.perform(
      get("/hi?name=lol")
    ).andExpect(
      status().isOk
    ).andExpect(
      content().string("Hello Lol")
    )
  }

  @Test
  fun sayHelloEmptyName() {
    mvc.perform(
      get("/hi?name=")
    ).andExpect(
      status().isOk
    ).andExpect(
      content().string("Hello who ever")
    )
  }

  @Test
  fun sayHelloWithNames() {
    mvc.perform(
      get("/hi?name=quinten mc   lacklin")
    ).andExpect(
      status().isOk
    ).andExpect(
      content().string("Hello Quinten Mc Lacklin")
    )
  }
}