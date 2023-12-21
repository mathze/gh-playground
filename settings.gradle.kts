pluginManagement {
  val kotlinVersion = "1.9.22"
  repositories {
    gradlePluginPortal()
  }

  plugins {
    // realization
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"

    // quality
    id("io.gitlab.arturbosch.detekt") version "1.23.4"

    // documentation
    id("org.jetbrains.dokka") version "1.9.10"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
  }
}

rootProject.name = "gh-playground"
