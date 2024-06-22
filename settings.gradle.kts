pluginManagement {
  val kotlinVersion = "1.9.23"
  repositories {
    gradlePluginPortal()
  }

  plugins {
    // realization
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion

    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.4"

    // quality
    id("io.gitlab.arturbosch.detekt") version "1.23.6"

    // documentation
    id("org.jetbrains.dokka") version "1.9.20"
    id("org.asciidoctor.jvm.convert") version "4.0.2"
  }
}

rootProject.name = "gh-playground"
