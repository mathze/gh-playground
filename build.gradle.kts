plugins {
  kotlin("jvm")
  kotlin("plugin.spring")

  id("io.spring.dependency-management")
  id("org.springframework.boot")

  jacoco
  id("org.sonarqube")
  id("io.gitlab.arturbosch.detekt")

  id("org.jetbrains.dokka")
  id("org.asciidoctor.jvm.convert")
}

repositories {
  mavenCentral()
  jcenter()
}

dependencyManagement {
  imports {
    mavenBom("org.junit:junit-bom:5.7.1")
  }
  dependencies {
    dependency("org.springframework.boot:spring-boot-starter-web:2.4.4") {
      exclude("org.springframework.boot:spring-boot-starter-tomcat")
    }
  }
}

dependencies {
  listOf("spring-boot-starter-web", "spring-boot-starter-jetty", "spring-boot-starter-actuator").forEach {
    implementation(group = "org.springframework.boot", name = it)
  }


  testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
  testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test-junit5")
  testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
}

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
  failFast = true
  config = files("$rootDir/detekt.yml")
  input = files("src/main/kotlin")

  reports {
    html.enabled = true
    xml.enabled = true
    txt.enabled = false
  }
}

tasks.test {
  useJUnitPlatform()
}

tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml") {
  outputDirectory.set(buildDir.resolve("docs/kdoc"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = JavaVersion.VERSION_11.toString()
    useIR = true
  }
}